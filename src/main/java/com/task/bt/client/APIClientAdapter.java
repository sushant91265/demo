package com.task.bt.client;

import com.task.bt.client.external.ExternalTransactionApi;
import com.task.bt.exception.InternalApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of {@link InternalTransactionApi} interface.
 * This class is responsible for fetching transactions from external API.
 * It uses {@link ExternalTransactionApi} to fetch transactions.
 */
@Component
@Slf4j
public class APIClientAdapter implements InternalTransactionApi {
    private final ExternalTransactionApi externalTransactionApi;
    @Value("${external.api.url}")
    private String URL;

    private final int PAGE = 1;
    private final int SIZE = 100;

    public APIClientAdapter(ExternalTransactionApi externalTransactionApi) {
        this.externalTransactionApi = externalTransactionApi;
    }

    @Override
    public <T> List<T> fetchTransactions(Class<T> responseType) {
        try {
            return externalTransactionApi.fetchTransactions(URL, PAGE, SIZE, responseType).get();
        } catch (RuntimeException | ExecutionException | InterruptedException e) {
            throw new InternalApiException("Error while fetching external transactions", e);
        }
    }
}
