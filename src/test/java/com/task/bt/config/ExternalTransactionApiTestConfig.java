package com.task.bt.config;

import com.task.bt.model.Transaction;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Test configuration for external transaction api.
 * It provides the class of the data model to be used for deserialization.
 */
@TestConfiguration
public class ExternalTransactionApiTestConfig {
    @Bean
    public Class<Transaction> transactionClass() {
        return Transaction.class;
    }
}
