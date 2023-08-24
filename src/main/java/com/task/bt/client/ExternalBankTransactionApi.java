package com.task.bt.client;

import com.task.bt.model.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExternalBankTransactionApi implements TransactionFetcher {
    @Override
    public List<Transaction> fetchTransactions() {
        // Call the actual external API here
        return Collections.unmodifiableList(new ArrayList<>());
    }
}
