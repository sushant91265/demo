package com.task.bt.client.external;

import com.task.bt.model.Transaction;

import java.util.List;

public interface ExternalTransactionApi {

    List<Transaction> fetchTransactions(String url);
}
