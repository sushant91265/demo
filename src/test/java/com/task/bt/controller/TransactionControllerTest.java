package com.task.bt.controller;

import com.task.bt.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMonthlyBalanceInvalidMonth() {
        int month = -1;
        int year = 2023;

        when(transactionService.getMonthlyBalance(month, year)).thenReturn(null);

        Double response = transactionController.getMonthlyBalance(month, year);
        assertNull(response);
    }

    @Test
    public void testGetMonthlyBalanceInvalidYear() {
        int month = 8;
        int year = -2023;

       when(transactionService.getMonthlyBalance(month, year)).thenReturn(null);

        Double response = transactionController.getMonthlyBalance(month, year);
        assertNull(response);
    }

    @Test
    public void testGetCumulativeBalanceInvalidMonth() {
        int month = -1;
        int year = 2023;

        when(transactionService.getCumulativeBalance(month, year)).thenReturn(null);

        Double response = transactionController.getCumulativeBalance(month, year);
        assertNull(response);
    }

    @Test
    public void testGetCumulativeBalanceInvalidYear() {
        int month = 8;
        int year = -2023;

        when(transactionService.getCumulativeBalance(month, year)).thenReturn(null);

        Double response = transactionController.getCumulativeBalance(month, year);
        assertNull(response);
    }

    @Test
    public void testGetMonthlyBalance() {
        int month = 8;
        int year = 2023;
        double mockBalance = 5000.0;

        when(transactionService.getMonthlyBalance(month, year)).thenReturn(mockBalance);

        Double response = transactionController.getMonthlyBalance(month, year);
        assertEquals(mockBalance, response);
    }

    @Test
    public void testGetCumulativeBalance() {
        int month = 8;
        int year = 2023;
        double mockBalance = 5000.0;

        when(transactionService.getCumulativeBalance(month, year)).thenReturn(mockBalance);

        Double response = transactionController.getCumulativeBalance(month, year);
        assertEquals(mockBalance, response);
    }

    @Test
    public void testGetMonthlyBalanceNotFound() {
        int month = 7;
        int year = 2023;

        when(transactionService.getMonthlyBalance(month, year)).thenReturn(null);

        Double response = transactionController.getMonthlyBalance(month, year);
        assertNull(response);
    }

    @Test
    public void testGetCumulativeBalanceNotFound() {
        int month = 7;
        int year = 2023;

        when(transactionService.getCumulativeBalance(month, year)).thenReturn(null);

        Double response = transactionController.getCumulativeBalance(month, year);
        assertNull(response);
    }

    @Test
    public void testGetMonthlyBalanceInvalidInput() {
        int month = 13;
        int year = 2023;

        when(transactionService.getMonthlyBalance(month, year)).thenReturn(null);

        Double response = transactionController.getMonthlyBalance(month, year);
        assertNull(response);
    }

    @Test
    public void testGetCumulativeBalanceInvalidInput() {
        int month = 13;
        int year = 2023;

        when(transactionService.getCumulativeBalance(month, year)).thenReturn(null);

        Double response = transactionController.getCumulativeBalance(month, year);
        assertNull(response);
    }
}
