package com.task.bt.calculator;

import com.task.bt.model.Transaction;

import java.util.List;

public interface TransactionCalculator {
    void processTransactions(List<Transaction> transactions);
    double getMonthlyBalance(int month, int year);
    double getCumulativeBalance(int endMonth, int endYear);
}
