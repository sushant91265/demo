package com.task.bt.service;

import com.task.bt.client.InternalTransactionApi;
import com.task.bt.exception.InternalApiException;
import com.task.bt.model.BalanceResult;
import com.task.bt.model.Transaction;
import com.task.bt.processor.TransactionProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class DefaultTransactionServiceTest {

    @Mock
    private InternalTransactionApi transactionFetcher;

    @Mock
    private TransactionProcessor transactionProcessor;

    private DefaultTransactionService transactionService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionService = new DefaultTransactionService(transactionFetcher, transactionProcessor);
    }

    @Test
    public void testBalancesForMonthlyBalance() throws InternalApiException {
        List<Transaction> transactions = Arrays.asList(new Transaction(100.00, "2023-09-09"),
                new Transaction(102.00, "2023-09-09"),
                new Transaction(102.00, "2023-10-09"),
                new Transaction(-2.00, "2023-09-09"));

        when(transactionFetcher.fetchTransactions(any(Class.class))).thenReturn(transactions);
        when(transactionProcessor.calculateSum(
                eq(transactions),
                any(Predicate.class)))
                .thenReturn(200.0);

        BalanceResult balanceResult = transactionService.getBalances(9, 2023);

        assertEquals(200.0, balanceResult.getMonthlyBalance());
    }

    @Test
    public void testBalancesForCumulativeBalance() throws InternalApiException {
        List<Transaction> transactions = Arrays.asList(new Transaction(100.00, "2023-09-09"),
                new Transaction(102.00, "2023-09-09"),
                new Transaction(102.00, "2023-10-09"),
                new Transaction(-2.00, "2023-09-09"));

        when(transactionFetcher.fetchTransactions(any(Class.class))).thenReturn(transactions);
        when(transactionProcessor.calculateSum(
                eq(transactions),
                any(Predicate.class)))
                .thenReturn(302.0);

        BalanceResult balanceResult = transactionService.getBalances(9, 2023);

        assertEquals(302.0, balanceResult.getMonthlyBalance());
    }
}

