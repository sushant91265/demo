package com.task.bt.service;

public interface TransactionService {
    Double getMonthlyBalance(int month, int year);
    Double getCumulativeBalance(int endMonth, int endYear);
}
