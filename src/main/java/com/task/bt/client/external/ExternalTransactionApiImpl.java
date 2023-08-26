package com.task.bt.client.external;

import com.task.bt.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class ExternalTransactionApiImpl {

    private final ExternalTransactionApi transactionFetcher;
    @Value("${external.api.retry.attempts}")
    private int retryAttempts;

    @Value("${external.api.retry.delay}")
    private int retryDelay;

    public ExternalTransactionApiImpl(@Qualifier("paginatedExternalTransactionApi") ExternalTransactionApi transactionFetcher) {
        this.transactionFetcher = transactionFetcher;
    }
    @Retryable(maxAttemptsExpression = "#{${externalTransactionApiImpl.retryAttempts}}",
               backoff = @Backoff(delayExpression = "#{${externalTransactionApiImpl.retryDelay}}"))
    public CompletableFuture<List<Transaction>> fetchTransactions(String url) {
        try {
            CompletableFuture<List<Transaction>> futureTransactions = CompletableFuture.supplyAsync(() ->
                    transactionFetcher.fetchTransactions(url)
            );

            return futureTransactions;
        } catch (ResourceAccessException ex) {
            log.error("External API request failed due to connectivity issues.", ex);
            return CompletableFuture.completedFuture(Collections.emptyList());
        }
    }
}
