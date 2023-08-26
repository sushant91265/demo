package com.task.bt.client.external;

import com.task.bt.exception.ExternalApiException;
import com.task.bt.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NonPaginatedExternalTransactionApiTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private NonPaginatedExternalTransactionApi api;

    @Test
    void testFetchTransactionsSuccess() {
        List<Transaction> expectedTransactions = List.of(new Transaction(10, "2023-08-08"));

        ResponseEntity<List<Transaction>> responseEntity = new ResponseEntity<>(expectedTransactions, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), any(ParameterizedTypeReference.class)))
                .thenReturn(responseEntity);

        List<Transaction> transactions = api.fetchTransactions("dummy_url", 0,0, Transaction.class);

        assertEquals(expectedTransactions, transactions);
    }

    @Test
    void testFetchTransactionsRestClientException() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), any(ParameterizedTypeReference.class)))
                .thenThrow(new RestClientException("Error"));

        assertThrows(ExternalApiException.class, () -> api.fetchTransactions("dummy_url",  0,0, Transaction.class));
    }

    @Test
    void testFetchTransactionsNullResponseBody() {
        ResponseEntity<List<Transaction>> responseEntity = new ResponseEntity<>(null, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), any(ParameterizedTypeReference.class)))
                .thenReturn(responseEntity);

        List<Transaction> transactions = api.fetchTransactions("dummy_url",  0,0, Transaction.class);

        assertTrue(transactions.isEmpty());
    }
}
