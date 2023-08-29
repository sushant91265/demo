package com.task.bt.service;

import com.task.bt.client.InternalTransactionApi;
import com.task.bt.exception.InternalApiException;
import com.task.bt.model.BalanceResult;
import com.task.bt.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class DefaultTransactionServiceTest {

    @Mock
    private InternalTransactionApi transactionFetcher;

    private DefaultTransactionService transactionService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionService = new DefaultTransactionService(transactionFetcher);
    }

    @Test
    public void testGetBalances() throws InternalApiException {
        int month = 9;
        int year = 2023;
        double monthlyBalance = 200.0;
        double cumulativeBalance = 302.0;

        List<Transaction> transactions = Arrays.asList(new Transaction(100.00, "2023-09-09"),
                new Transaction(102.00, "2023-09-09"),
                new Transaction(102.00, "2023-10-10"),
                new Transaction(-2.00, "2023-09-09"));

        when(transactionFetcher.fetchTransactions(any(Class.class))).thenReturn(transactions);

        BalanceResult balanceResult = transactionService.getBalances(month, year);

        assertEquals(monthlyBalance, balanceResult.getMonthlyBalance());
        assertEquals(cumulativeBalance, balanceResult.getCumulativeBalance());
    }

    @Test
    public void testGetBalancesWithNoMatchingTxn() throws InternalApiException {
        int month = 9;
        int year = 2024;
        double monthlyBalance = 0.0;
        double cumulativeBalance = 0.0;

        List<Transaction> transactions = Arrays.asList(new Transaction(100.00, "2023-09-09"),
                new Transaction(102.00, "2023-09-09"),
                new Transaction(102.00, "2023-10-10"),
                new Transaction(-2.00, "2023-09-09"));

        when(transactionFetcher.fetchTransactions(any(Class.class))).thenReturn(transactions);

        BalanceResult balanceResult = transactionService.getBalances(month, year);

        assertEquals(monthlyBalance, balanceResult.getMonthlyBalance());
        assertEquals(cumulativeBalance, balanceResult.getCumulativeBalance());
    }
}

