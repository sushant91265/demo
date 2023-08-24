package com.task.bt.client;

import com.task.bt.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class APIClientAdapter implements TransactionFetcher {
    private TransactionFetcher externalApi;

    public APIClientAdapter(TransactionFetcher externalApi) {
        this.externalApi = externalApi;
    }
    @Override
    public List<Transaction> fetchTransactions() {
        List<Transaction> bankTransactions = externalApi.fetchTransactions();
        return bankTransactions;
    }
}
