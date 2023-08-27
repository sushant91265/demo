package com.task.bt.service;

import com.task.bt.model.Transaction;

import java.util.List;

public interface TransactionService {
    <T> List<T> fetchTransactions(Class<T> responseType);
    <T> void processTransactions(List<T> transactions);

    Double getMonthlyBalance(int month, int year);
    Double getCumulativeBalance(int endMonth, int endYear);
}
