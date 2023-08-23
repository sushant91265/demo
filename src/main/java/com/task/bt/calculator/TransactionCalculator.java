package com.task.bt.calculator;

public interface TransactionCalculator {
    double getMonthlyBalance(int month, int year);
    double getCumulativeBalance(int endMonth, int endYear);
}
