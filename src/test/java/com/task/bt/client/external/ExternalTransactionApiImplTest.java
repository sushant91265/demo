package com.task.bt.client.external;

import com.task.bt.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.ResourceAccessException;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import static org.awaitility.Awaitility.*;

class ExternalTransactionApiImplTest {

    private ExternalTransactionApi transactionFetcher;
    private ExternalTransactionApiImpl externalTransactionApi;

    @BeforeEach
    void setUp() {
        transactionFetcher = mock(ExternalTransactionApi.class);
        externalTransactionApi = new ExternalTransactionApiImpl(transactionFetcher);
    }

    @Test
    void testFetchTransactionsWithRetries() {
        String apiUrl = "https://some-api-url.com";

        // Configure the mock to throw ResourceAccessException for the first 3 attempts
        when(transactionFetcher.fetchTransactions(apiUrl))
                .thenThrow(new ResourceAccessException("Connection error"))
                .thenThrow(new ResourceAccessException("Connection error"))
                .thenThrow(new ResourceAccessException("Connection error"))
                .thenReturn(Collections.singletonList(new Transaction(100,"2023-08-08")));

        await().atMost(3, SECONDS).untilAsserted(() -> {
            CompletableFuture<List<Transaction>> result = externalTransactionApi.fetchTransactions(apiUrl);

            // Verify that the fetchTransactions method was called 4 times (3 retries + 1 success)
            verify(transactionFetcher, times(4)).fetchTransactions(apiUrl);
            assertEquals(1, result.get().size());
            assertEquals(100, result.get().get(0).getAmount());
            assertEquals("2023-08-08", result.get().get(0).getDate().toString());
        });
    }
}
