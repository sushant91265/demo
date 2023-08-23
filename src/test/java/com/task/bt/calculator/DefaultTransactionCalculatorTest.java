package com.task.bt.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNull;

public class DefaultTransactionCalculatorTest {

    @InjectMocks
    DefaultTransactionCalculator defaultTransactionCalculator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getMonthlyBalance() {
        assertNull(defaultTransactionCalculator.getMonthlyBalance(0,0));
    }

    @Test
    void getCumulativeBalance() {
        assertNull(defaultTransactionCalculator.getCumulativeBalance(0,0));
    }
}