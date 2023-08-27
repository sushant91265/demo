package com.task.bt.config;

import com.task.bt.model.Transaction;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ExternalTransactionApiTestConfig {
    @Bean
    public Class<Transaction> transactionClass() {
        return Transaction.class;
    }
}
