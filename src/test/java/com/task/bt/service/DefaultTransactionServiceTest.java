package com.task.bt.service;

import com.task.bt.calculator.TransactionCalculator;
import com.task.bt.client.TransactionFetcher;
import com.task.bt.model.Transaction;
import com.task.bt.processor.TransactionProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class DefaultTransactionServiceTest {

    @Mock
    private TransactionFetcher transactionFetcher;

    @Mock
    private TransactionProcessor transactionProcessor;

    @Mock
    private TransactionCalculator transactionCalculator;

    @InjectMocks
    private DefaultTransactionService transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchTransactions() {
        List<Transaction> mockTransactions = new ArrayList<>();
        when(transactionFetcher.fetchTransactions()).thenReturn(mockTransactions);

        List<Transaction> result = transactionService.fetchTransactions();

        assertEquals(mockTransactions, result);
    }

    @Test
    public void testProcessTransactions() {
        List<Transaction> mockTransactions = new ArrayList<>();
        doNothing().when(transactionProcessor).processTransactions(mockTransactions);

        transactionService.processTransactions(mockTransactions);

        verify(transactionProcessor, times(1)).processTransactions(mockTransactions);
    }

    @Test
    public void testGetMonthlyBalance() {
        int month = 8;
        int year = 2023;
        double mockBalance = 5000.0;

        when(transactionCalculator.getMonthlyBalance(month, year)).thenReturn(mockBalance);

        Double result = transactionService.getMonthlyBalance(month, year);

        assertEquals(mockBalance, result);
    }

    @Test
    public void testGetCumulativeBalance() {
        int endMonth = 8;
        int endYear = 2023;
        double mockBalance = 15000.0;

        when(transactionCalculator.getCumulativeBalance(endMonth, endYear)).thenReturn(mockBalance);

        Double result = transactionService.getCumulativeBalance(endMonth, endYear);

        assertEquals(mockBalance, result);
    }
}

