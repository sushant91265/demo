package com.task.bt.client.external;

import com.task.bt.config.ExternalTransactionApiTestConfig;
import com.task.bt.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.ResourceAccessException;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.awaitility.Awaitility.*;

@ContextConfiguration(classes = ExternalTransactionApiTestConfig.class)
@ExtendWith(SpringExtension.class)
class ExternalTransactionApiImplTest {

    private TransactionFetcherStrategy transactionFetcher;
    private ExternalTransactionApi externalTransactionApi;
    @Autowired
    private Class<Transaction> dataModelClass;


    @BeforeEach
    void setUp() {
        transactionFetcher = mock(TransactionFetcherStrategy.class);
        externalTransactionApi = new ExternalTransactionApiImpl(transactionFetcher);

        Mockito.reset(transactionFetcher);
    }

    @Test
    void testFetchTransactionsWithRetries() {

        when(transactionFetcher.fetchTransactions(anyString(), anyInt(), anyInt(), any(Class.class)))
                .thenThrow(new ResourceAccessException("Connection error"))
                .thenThrow(new ResourceAccessException("Connection error"))
                .thenThrow(new ResourceAccessException("Connection error"))
                .thenReturn(Collections.singletonList(new Transaction(100.0,"2023-08-08")));

        await().atMost(3, SECONDS).untilAsserted(() -> {
            CompletableFuture<List<Transaction>> result = externalTransactionApi.fetchTransactions("dummy_url", 1, 10, dataModelClass);

            verify(transactionFetcher, times(4)).fetchTransactions(anyString(), anyInt(), anyInt(), any(Class.class));
            assertEquals(1, result.get().size());
            assertEquals(100, result.get().get(0).getAmount());
            assertEquals("2023-08-08", result.get().get(0).getDate().toString());
        });
    }

    @Test
    void testFetchTransactionsResourceAccessException() {
        when(transactionFetcher.fetchTransactions(anyString(), anyInt(), anyInt(), any(Class.class)))
                .thenThrow(new ResourceAccessException("Connection error"));

        CompletableFuture<List<Transaction>> result = externalTransactionApi.fetchTransactions("dummy_url", 1, 10, dataModelClass);
        ExecutionException executionException = assertThrows(ExecutionException.class, () -> result.get(1, SECONDS));
        Throwable actualException = executionException.getCause();

        assertTrue(actualException instanceof ResourceAccessException);
    }
}
