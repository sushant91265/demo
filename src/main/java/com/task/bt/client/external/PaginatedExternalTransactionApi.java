package com.task.bt.client.external;

import com.task.bt.model.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
public class PaginatedExternalTransactionApi implements ExternalTransactionApi {

    private final RestTemplate restTemplate;

    public PaginatedExternalTransactionApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Transaction> fetchTransactions(String url) {
        // Logic to fetch paginated transactions
        return Collections.emptyList();
    }
}
