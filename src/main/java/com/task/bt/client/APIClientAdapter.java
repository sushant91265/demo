package com.task.bt.client;

import com.task.bt.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class APIClientAdapter implements InternalTransactionApi {
    private ExternalTransactionApi externalApi;

    public APIClientAdapter(ExternalTransactionApi externalApi) {
        this.externalApi = externalApi;
    }
    @Override
    public List<Transaction> fetchTransactions() {
        return externalApi.fetchTransactions();
    }
}
