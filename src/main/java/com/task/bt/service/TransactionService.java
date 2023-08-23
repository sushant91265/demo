package com.task.bt.service;

import com.task.bt.model.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> fetchTransactions();
    void processTransactions(List<Transaction> transactions);

    double getMonthlyBalance(int month, int year);
    double getCumulativeBalance(int endMonth, int endYear);
}
