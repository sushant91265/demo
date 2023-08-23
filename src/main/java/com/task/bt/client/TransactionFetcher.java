package com.task.bt.client;

import com.task.bt.model.Transaction;

import java.util.List;

public interface TransactionFetcher {
    List<Transaction> fetchTransactions();
}
