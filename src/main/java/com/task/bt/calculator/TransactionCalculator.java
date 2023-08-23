package com.task.bt.calculator;

public interface TransactionCalculator {
    Double getMonthlyBalance(int month, int year);
    Double getCumulativeBalance(int endMonth, int endYear);
}
