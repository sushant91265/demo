package com.task.bt.service;

import com.task.bt.client.InternalTransactionApi;
import com.task.bt.config.ExternalTransactionApiTestConfig;
import com.task.bt.model.Transaction;
import com.task.bt.processor.TransactionProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ExternalTransactionApiTestConfig.class)
public class DefaultTransactionServiceTest {

    @Mock
    private InternalTransactionApi transactionFetcher;

    @Mock
    private TransactionProcessor transactionProcessor;


    private DefaultTransactionService transactionService;

    @Autowired
    private Class<Transaction> dataModelClass;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionService = new DefaultTransactionService(transactionFetcher, transactionProcessor);
    }

    @Test
    public void testFetchTransactions() {
        List<Transaction> mockTransactions = Arrays.asList(new Transaction(100.00, "2023-09-09")
                                                          , new Transaction(102.00, "2023-09-09"));

        when(transactionFetcher.fetchTransactions(dataModelClass)).thenReturn(mockTransactions);

        List<Transaction> result = transactionService.fetchTransactions(dataModelClass);

        assertEquals(mockTransactions.size(), result.size());
        assertEquals(mockTransactions.get(0).getAmount(), result.get(0).getAmount());
        assertEquals(mockTransactions.get(1).getAmount(), result.get(1).getAmount());
    }

    @Test
    public void testFetchTransactionsWithError() {
        when(transactionFetcher.fetchTransactions(dataModelClass)).thenThrow(new RuntimeException("Error while fetching transactions"));

        assertThrows(RuntimeException.class, () -> transactionService.fetchTransactions(dataModelClass));
    }

    @Test
    public void testGetMonthlyBalance() {
        int month = 9;
        int year = 2023;
        double mockBalance = 202.0;

        List<Transaction> mockTransactions = Arrays.asList(new Transaction(100.00, "2023-09-09"),
                                                            new Transaction(102.00, "2023-09-09"));

        Predicate<Transaction> txnPredicate = txn -> txn.getMonth() == month && txn.getYear() == year;

        when(transactionFetcher.fetchTransactions(dataModelClass)).thenReturn(mockTransactions);
        when(transactionProcessor.calculateSum(mockTransactions, txnPredicate)).thenReturn(mockBalance);

        Double result = transactionService.getMonthlyBalance(month, year);

        assertEquals(mockBalance, result);
    }

    @Test
    public void testGetCumulativeBalance() {
        int endMonth = 9;
        int endYear = 2023;
        double mockBalance = 202.0;

        List<Transaction> mockTransactions = Arrays.asList(new Transaction(100.00, "2023-09-09")
                                                          , new Transaction(102.00, "2023-09-09"));

        when(transactionFetcher.fetchTransactions(dataModelClass)).thenReturn(mockTransactions);
        when(transactionProcessor.calculateSum(mockTransactions,
                txn -> {
                    if(txn.getYear() == endYear) return txn.getMonth() <= endMonth;
                    return txn.getYear() <= endYear;
                })).thenReturn(mockBalance);

        Double result = transactionService.getCumulativeBalance(endMonth, endYear);

        assertEquals(mockBalance, result);
    }
}

