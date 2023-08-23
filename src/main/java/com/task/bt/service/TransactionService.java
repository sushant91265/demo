package com.task.bt.service;

import com.task.bt.model.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> fetchTransactions();
    void processTransactions(List<Transaction> transactions);

    Double getMonthlyBalance(int month, int year);
    Double getCumulativeBalance(int endMonth, int endYear);
}
