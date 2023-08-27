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
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ExternalTransactionApiTestConfig.class)
public class APIClientAdapterTest {

    @Mock
    private ExternalTransactionApi mockExternalTransactionApi;

    @Autowired
    private Class<Transaction> dataModelClass;

    @InjectMocks
    private APIClientAdapter adapter;

    @Value("${external.api.url}")
    private String url = "https://some-api-url.com";

    @Test
    void testFetchTransactions() {

        List<Transaction> mockTransactions = Arrays.asList(
                new Transaction(100.0, "2023-08-01"),
                new Transaction(200.0, "2023-08-02")
        );
        CompletableFuture<List<Transaction>> futureMockTransactions = CompletableFuture.completedFuture(mockTransactions);

        when(mockExternalTransactionApi.fetchTransactions(eq(url), eq(1), eq(100), eq(dataModelClass)))
                .thenReturn(futureMockTransactions);

        List<Transaction> result = adapter.fetchTransactions();

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

        assertThrows(InternalApiException.class, () -> adapter.fetchTransactions());
    }
}