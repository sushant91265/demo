package com.task.bt.client;

import com.task.bt.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class APIClientAdapterTest {

    private ExternalTransactionApi mockApi;
    private APIClientAdapter adapter;

    @BeforeEach
    void setUp() {
        mockApi = mock(ExternalTransactionApi.class);
        adapter = new APIClientAdapter(mockApi);
    }

    @Test
    void testFetchTransactions() {
        List<Transaction> mockTransactions = Arrays.asList(
                new Transaction(100.0, "2023-08-01"),
                new Transaction(200.0, "2023-08-02")
        );
        when(mockApi.fetchTransactions()).thenReturn(mockTransactions);

        List<Transaction> bankTransactions = adapter.fetchTransactions();

        assertEquals(2, bankTransactions.size());
        assertEquals(100.0, bankTransactions.get(0).getAmount());
        assertEquals("2023-08-01", bankTransactions.get(0).getDate().toString());
    }
}