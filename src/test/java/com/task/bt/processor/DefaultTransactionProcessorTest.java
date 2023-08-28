package com.task.bt.processor;

import com.task.bt.model.Transaction;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class DefaultTransactionProcessorTest {

    private DefaultTransactionProcessor processor = new DefaultTransactionProcessor();

    @Test
    public void testCalculateBalanceWithTransactions() {

        List<Transaction> transactions = Arrays.asList(
                new Transaction(100.0, "2023-08-01"),
                new Transaction(200.0, "2023-08-02"),
                new Transaction(-50.0, "2023-08-03")
        );

        int month = 8;
        int year = 2023;

        Predicate<Transaction> filter = txn -> txn.getMonth() == month && txn.getYear() == year;
        Double balance = processor.calculateSum(transactions, filter);

        assertEquals(250.0, balance);
    }

    @Test
    public void testCalculateBalanceWithEmptyList() {

        Predicate<Transaction> filter = transaction -> true;
        Double balance = processor.calculateSum(Collections.emptyList(), filter);

        assertEquals(0.0, balance, 0.01);
    }
}
