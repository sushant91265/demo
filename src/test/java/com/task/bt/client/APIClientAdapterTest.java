package com.task.bt.client;

import com.task.bt.client.external.ExternalTransactionApi;
import com.task.bt.config.ExternalTransactionApiTestConfig;
import com.task.bt.exception.InternalApiException;
import com.task.bt.model.Transaction;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ExternalTransactionApiTestConfig.class)
public class APIClientAdapterTest {

    @Mock
    private ExternalTransactionApi mockExternalTransactionApi;

    @Autowired
    private Class<Transaction> dataModelClass;

    @InjectMocks
    private APIClientAdapter adapter;

    @Test
    void testFetchTransactions() {
        List<Transaction> mockTransactions = Arrays.asList(
                new Transaction(100.0, "2023-08-01"),
                new Transaction(200.0, "2023-08-02")
        );
        CompletableFuture<List<Transaction>> futureMockTransactions = CompletableFuture.completedFuture(mockTransactions);

        ReflectionTestUtils.setField(adapter, "url", "https://some-api-url.com");
        when(mockExternalTransactionApi.fetchTransactions(anyString(), anyInt(), anyInt(), any(Class.class)))
                .thenReturn(futureMockTransactions);

        List<Transaction> result = adapter.fetchTransactions(dataModelClass);

        assertEquals(2, result.size());
        assertEquals(100.0, result.get(0).getAmount());
        assertEquals("2023-08-01", result.get(0).getDate().toString());
    }

    @Test
    void testFetchTransactionsWithError() {

        CompletableFuture<List<Transaction>> futureMockTransactions = new CompletableFuture<>();
        futureMockTransactions.completeExceptionally(new RuntimeException("Test exception"));

        when(mockExternalTransactionApi.fetchTransactions(anyString(), anyInt(), anyInt(), eq(dataModelClass)))
                .thenReturn(futureMockTransactions);

        assertThrows(InternalApiException.class, () -> adapter.fetchTransactions(dataModelClass));
    }
}