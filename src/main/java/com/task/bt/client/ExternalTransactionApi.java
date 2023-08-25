package com.task.bt.client;

import com.task.bt.model.Transaction;

import java.util.List;

public interface ExternalTransactionApi {
    List<Transaction> fetchTransactions();

    List<Transaction> fetchTransactions(int page, int size);


}
