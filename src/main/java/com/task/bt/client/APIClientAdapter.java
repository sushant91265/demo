package com.task.bt.client;

import com.task.bt.client.external.ExternalTransactionApi;
import com.task.bt.exception.InternalApiException;
import com.task.bt.model.Transaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class APIClientAdapter implements InternalTransactionApi {
    private final ExternalTransactionApi externalApi;
    @Value("${external.api.url}")
    private String url;

    public APIClientAdapter(@Qualifier("paginatedExternalTransactionApi") ExternalTransactionApi externalApi) {
        this.externalApi = externalApi;
    }
    @Override
    public List<Transaction> fetchTransactions() {
        try {
            return externalApi.fetchTransactions(url);
        } catch (Exception e) {
            throw new InternalApiException("Error fetching external transactions", e);
        }
    }
}
