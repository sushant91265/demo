package com.task.bt.service;

import com.task.bt.calculator.TransactionCalculator;
import com.task.bt.client.TransactionFetcher;
import com.task.bt.model.Transaction;
import com.task.bt.processor.TransactionProcessor;

import java.util.List;

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
    public double getMonthlyBalance(int month, int year) {
        return transactionCalculator.getMonthlyBalance(month, year);
    }

    @Override
    public double getCumulativeBalance(int endMonth, int endYear) {
        return transactionCalculator.getCumulativeBalance(endMonth, endYear);
    }
}
