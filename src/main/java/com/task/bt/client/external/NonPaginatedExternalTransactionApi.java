package com.task.bt.client.external;

import com.task.bt.exception.ExternalApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class NonPaginatedExternalTransactionApi implements TransactionFetcherStrategy {
    private final RestTemplate restTemplate;

    public NonPaginatedExternalTransactionApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public <T> List<T> fetchTransactions(String url, int page, int size, Class<T> responseType) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<List<T>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<T>>() {
                    });

            return Optional.ofNullable(response.getBody())
                    .orElse(Collections.emptyList());

        } catch (RestClientException ex) {
            throw new ExternalApiException("External API request failed due to server error.", ex);
        }
    }
}
