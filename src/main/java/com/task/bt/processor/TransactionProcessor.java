package com.task.bt.processor;

import com.task.bt.model.Transaction;

import java.util.List;

public interface TransactionProcessor {
    void processTransactions(List<Transaction> transactions);
}
