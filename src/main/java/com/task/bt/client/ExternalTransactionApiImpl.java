package com.task.bt.client;

import com.task.bt.exception.ExternalApiException;
import com.task.bt.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class ExternalTransactionApiImpl implements ExternalTransactionApi {
    @Override
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 3000))
    public List<Transaction> fetchTransactions(int page, int size) {
        try {
            // Call the external API
            return Collections.unmodifiableList(new ArrayList<>());
        } catch (Exception e) {
            throw new ExternalApiException("Error fetching external transactions", e);
        }
    }

    @Override
    public List<Transaction> fetchTransactions() {
        try {
            // Call the external API
            return Collections.unmodifiableList(new ArrayList<>());
        } catch (Exception e) {
            throw new ExternalApiException("Error fetching external transactions", e);
        }
    }
}
