package com.task.bt.client.external;

import com.task.bt.config.ExternalTransactionApiTestConfig;
import com.task.bt.exception.ExternalApiException;
import com.task.bt.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ExternalTransactionApiTestConfig.class)
public class NonPaginatedExternalTransactionApiTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private NonPaginatedExternalTransactionApi api;

    private final String apiUrl = "dummy_url";

    @Autowired
    private Class<Transaction> dataModelClass;

    @Test
    void testFetchTransactionsSuccess() {
        List<Transaction> expectedTransactions = List.of(new Transaction(10.00, "2023-08-08"));

        ResponseEntity<List<Transaction>> responseEntity = new ResponseEntity<>(expectedTransactions, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), any(ParameterizedTypeReference.class)))
                .thenReturn(responseEntity);

        List<Transaction> transactions = api.fetchTransactions(apiUrl, 1,10, dataModelClass);

        assertEquals(expectedTransactions, transactions);
    }

    @Test
    void testFetchTransactionsRestClientException() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), any(ParameterizedTypeReference.class)))
                .thenThrow(new RestClientException("Error"));

        assertThrows(ExternalApiException.class, () -> api.fetchTransactions(apiUrl,  1,10, dataModelClass));
    }

    @Test
    void testFetchTransactionsNullResponseBody() {
        ResponseEntity<List<Transaction>> responseEntity = new ResponseEntity<>(null, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), any(ParameterizedTypeReference.class)))
                .thenReturn(responseEntity);

        List<Transaction> transactions = api.fetchTransactions(apiUrl,  0,0, dataModelClass);

        assertTrue(transactions.isEmpty());
    }
}
