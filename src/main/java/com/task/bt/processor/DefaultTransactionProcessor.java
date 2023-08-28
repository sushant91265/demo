package com.task.bt.processor;

import com.task.bt.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

/**
 * Implementation of {@link TransactionProcessor} interface.
 * This class is responsible for handling any processing logic for the transactions.
 */
@Component
public class DefaultTransactionProcessor implements TransactionProcessor {
    @Override
    public <T> Double calculateSum(List<T> transactions, Predicate<T> filter) {
        return transactions.stream()
                .filter(filter)
                .mapToDouble(txn -> ((Transaction) txn).getAmount())
                .sum();
    }
}
