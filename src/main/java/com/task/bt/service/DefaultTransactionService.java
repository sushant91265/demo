package com.task.bt.service;

import com.task.bt.calculator.TransactionCalculator;
import com.task.bt.client.TransactionFetcher;
import com.task.bt.model.Transaction;
import com.task.bt.processor.TransactionProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
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
        return transactionFetcher.fetchTransactions();
    }

    @Override
    public void processTransactions(List<Transaction> transactions) {
        transactionProcessor.processTransactions(transactions);
    }

    @Override
    public Double getMonthlyBalance(int month, int year) {
        return transactionCalculator.getMonthlyBalance(month, year);
    }

    @Override
    public Double getCumulativeBalance(int endMonth, int endYear) {
        return transactionCalculator.getCumulativeBalance(endMonth, endYear);
    }
}
