package com.task.bt.service;

import com.task.bt.client.InternalTransactionApi;
import com.task.bt.config.ExternalTransactionApiTestConfig;
import com.task.bt.exception.InternalApiException;
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
    public void testGetMonthlyBalance() throws InternalApiException {
        List<Transaction> transactions = Arrays.asList(new Transaction(100.00, "2023-09-09"),
                new Transaction(102.00, "2023-09-09"));

        when(transactionFetcher.fetchTransactions(Transaction.class)).thenReturn(transactions);
        when(transactionProcessor.calculateSum(
                eq(transactions),
                any(Predicate.class)))
                .thenReturn(202.0);

        Double balance = transactionService.getMonthlyBalance(8, 2023);

        assertEquals(202.0, balance);
        verify(transactionProcessor, times(1)).calculateSum(anyList(), any(Predicate.class));
    }

    @Test
    public void testGetCumulativeBalance() {
        int endMonth = 9;
        int endYear = 2023;
        double mockBalance = 202.0;

        List<Transaction> mockTransactions = Arrays.asList(new Transaction(100.00, "2023-09-09")
                                                          , new Transaction(102.00, "2023-09-09"));

        when(transactionFetcher.fetchTransactions(dataModelClass)).thenReturn(mockTransactions);
        when(transactionProcessor.calculateSum(
                eq(mockTransactions),
                any(Predicate.class)))
                .thenReturn(202.0);

        Double result = transactionService.getCumulativeBalance(endMonth, endYear);

        assertEquals(mockBalance, result);
        verify(transactionProcessor, times(1)).calculateSum(anyList(), any(Predicate.class));
    }
}

