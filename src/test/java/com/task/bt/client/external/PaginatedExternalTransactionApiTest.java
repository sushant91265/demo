package com.task.bt.client.external;

import com.task.bt.config.ExternalTransactionApiTestConfig;
import com.task.bt.exception.ExternalApiException;
import com.task.bt.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ExternalTransactionApiTestConfig.class)
public class PaginatedExternalTransactionApiTest {

    @Mock
    private RestTemplate restTemplate;

    private final String apiUrl = "http://localhost:8080/transactions";

    @Autowired
    private Class<Transaction> dataModelClass;

    private TransactionFetcherStrategy paginatedApi;
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        paginatedApi = new PaginatedExternalTransactionApi(restTemplate);
    }

    @Test
    void testFetchTransactionsWithPagination() {
        Transaction[] transactionsArray = { new Transaction(10.0,"2022-09-09"), new Transaction(11.0,"2022-09-09") };
        ResponseEntity<List<Transaction>> responseEntity = new ResponseEntity<>(List.of(transactionsArray), HttpStatus.OK);

        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<List<Transaction>>>any()))
                .thenReturn(responseEntity);

        List<Transaction> transactions = paginatedApi.fetchTransactions(apiUrl, 1, 10, dataModelClass);

        assertEquals(transactionsArray.length, transactions.size());
        assertEquals(transactionsArray[0].getAmount(), transactions.get(0).getAmount());
        assertEquals(transactionsArray[0].getDate(), transactions.get(0).getDate());
    }

    @Test
    void testFetchTransactionsWithPaginationWithEmptyResponse() {
        ResponseEntity<List<Transaction>> responseEntity = new ResponseEntity<>(List.of(), HttpStatus.OK);

        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class),
                ArgumentMatchers.<ParameterizedTypeReference<List<Transaction>>>any()))
                .thenReturn(responseEntity);

        List<Transaction> transactions = paginatedApi.fetchTransactions(apiUrl, 1, 10, dataModelClass);

        assertEquals(0, transactions.size());
    }

    @Test
    void testFetchTransactionsRestClientException() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), any(ParameterizedTypeReference.class)))
                .thenThrow(new RestClientException("Error"));

        assertThrows(ExternalApiException.class, () -> paginatedApi.fetchTransactions(apiUrl, 1, 10, dataModelClass));
    }
}
