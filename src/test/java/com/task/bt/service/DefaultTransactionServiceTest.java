package com.task.bt.service;

import com.task.bt.calculator.TransactionCalculator;
import com.task.bt.client.InternalTransactionApi;
import com.task.bt.config.ExternalTransactionApiTestConfig;
import com.task.bt.model.Transaction;
import com.task.bt.processor.TransactionProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ExternalTransactionApiTestConfig.class)
public class DefaultTransactionServiceTest {

    @Mock
    private InternalTransactionApi transactionFetcher;

    @Mock
    private TransactionProcessor transactionProcessor;

    @Mock
    private TransactionCalculator transactionCalculator;

    @InjectMocks
    private DefaultTransactionService transactionService;

    @Autowired
    private Class<Transaction> dataModelClass;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchTransactions() {
        List<Transaction> mockTransactions = Arrays.asList(new Transaction(100.00, "2023-09-09")
                                                          , new Transaction(102.00, "2023-09-09"));

        when(transactionFetcher.fetchTransactions(dataModelClass)).thenReturn(mockTransactions);

        List<Transaction> result = transactionService.fetchTransactions(dataModelClass);

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

