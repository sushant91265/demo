package com.task.bt.client;

import com.task.bt.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class APIClientAdapter implements TransactionFetcher {
    @Override
    public List<Transaction> fetchTransactions() {
        //TODO: implement this method
        try {
            return new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch transactions", e);
        }
    }
}
