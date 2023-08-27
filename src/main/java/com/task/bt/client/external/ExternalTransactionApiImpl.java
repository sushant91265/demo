package com.task.bt.client.external;

import com.task.bt.exception.ExternalApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation of {@link ExternalTransactionApi} interface.
 * This class is responsible for fetching transactions from external API.
 * It uses {@link TransactionFetcherStrategy} to fetch transactions.
 * It uses Spring Retry to retry the external API call in case of failure.
 */
@Component
@Slf4j
public class ExternalTransactionApiImpl implements ExternalTransactionApi {

    private final TransactionFetcherStrategy transactionFetcherStrategy;
    @Value("${external.api.retry.attempts}")
    private int retryAttempts;

    @Value("${external.api.retry.delay}")
    private int retryDelay;

    public ExternalTransactionApiImpl(@Qualifier("paginatedExternalTransactionApi") TransactionFetcherStrategy transactionFetcherStrategy) {
        this.transactionFetcherStrategy = transactionFetcherStrategy;
    }

    @Override
    public <T> CompletableFuture<List<T>> fetchTransactions(String url, int page, int size, Class<T> responseType) {
        return fetchTransactionsWithRetry(url, page, size, responseType);
    }

    @Retryable(maxAttemptsExpression = "#{${externalTransactionApiImpl.retryAttempts}}",
               backoff = @Backoff(delayExpression = "#{${externalTransactionApiImpl.retryDelay}}"))
    protected  <T> CompletableFuture<List<T>> fetchTransactionsWithRetry(String url, int page, int size, Class<T> responseType) {
        try {
                CompletableFuture<List<T>> futureTransactions = CompletableFuture.supplyAsync(() ->
                        transactionFetcherStrategy.fetchTransactions(url, page, size, responseType));
                return futureTransactions;

        } catch (ResourceAccessException ex) {
            throw new ExternalApiException("External API request failed due to connectivity issues.", ex);
        }
    }
}
