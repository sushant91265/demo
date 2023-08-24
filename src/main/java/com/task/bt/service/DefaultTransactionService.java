package com.task.bt.service;

import com.task.bt.calculator.TransactionCalculator;
import com.task.bt.client.TransactionFetcher;
import com.task.bt.model.Transaction;
import com.task.bt.processor.TransactionProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultTransactionService implements TransactionService {
    private TransactionFetcher transactionFetcher;
    private TransactionProcessor transactionProcessor;
    private TransactionCalculator transactionCalculator;

    public DefaultTransactionService(TransactionFetcher transactionFetcher, TransactionCalculator transactionCalculator,
                                     TransactionProcessor transactionProcessor) {
        this.transactionFetcher = transactionFetcher;
        this.transactionCalculator = transactionCalculator;
        this.transactionProcessor = transactionProcessor;
    }

    @Override
    public List<Transaction> fetchTransactions() {
        //TODO: handle exceptions?
        return transactionFetcher.fetchTransactions();
    }

    @Override
    public void processTransactions(List<Transaction> transactions) {
        transactionProcessor.processTransactions(transactions);
    }

    @Override
    public Double getMonthlyBalance(int month, int year) {
        if (month < 1 || year < 0 || month > 12) throw new IllegalArgumentException("Invalid Month or year");

        return transactionCalculator.getMonthlyBalance(month, year);
    }

    @Override
    public Double getCumulativeBalance(int endMonth, int endYear) {
        if (endMonth < 1 || endYear < 0 || endMonth > 12) throw new IllegalArgumentException("Invalid Month or year");

        return transactionCalculator.getCumulativeBalance(endMonth, endYear);
    }
}
