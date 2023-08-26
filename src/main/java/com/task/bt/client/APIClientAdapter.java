package com.task.bt.client;

import com.task.bt.client.external.ExternalTransactionApi;
import com.task.bt.exception.InternalApiException;
import com.task.bt.model.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class APIClientAdapter implements InternalTransactionApi {
    private final ExternalTransactionApi externalTransactionApi;
    @Value("${external.api.url}")
    private String url;

    public APIClientAdapter(ExternalTransactionApi externalTransactionApi) {
        this.externalTransactionApi = externalTransactionApi;
    }
    @Override
    public List<Transaction> fetchTransactions() {
        try {
            return externalTransactionApi.fetchTransactions(url, 1,100, Transaction.class).get();
        } catch (Exception e) {
            throw new InternalApiException("Error while fetching external transactions", e);
        }
    }
}
