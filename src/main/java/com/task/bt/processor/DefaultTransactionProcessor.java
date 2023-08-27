package com.task.bt.processor;

import com.task.bt.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link TransactionProcessor} interface.
 * This class is responsible for handling any pre-processing logic for the transactions.
 */
@Component
public class DefaultTransactionProcessor implements TransactionProcessor {
    @Override
    public void processTransactions(List<Transaction> transactions) {
        //TODO: write any processing logic for the transactions
    }
}
