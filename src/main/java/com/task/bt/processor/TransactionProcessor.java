package com.task.bt.processor;

import java.util.List;
import java.util.function.Predicate;

public interface TransactionProcessor {

    /**
     * Returns the balance of the transactions that match the given predicate.
     *
     * @param transactions list of transactions
     * @param filter       predicate to filter the transactions
     * @return balance of the transactions
     */
    <T> Double calculateSum(List<T> transactions, Predicate<T> filter);
}
