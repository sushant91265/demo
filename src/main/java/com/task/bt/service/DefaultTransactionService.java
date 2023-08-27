package com.task.bt.service;

import com.task.bt.calculator.TransactionCalculator;
import com.task.bt.client.InternalTransactionApi;
import com.task.bt.exception.ServiceException;
import com.task.bt.model.Transaction;
import com.task.bt.processor.TransactionProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DefaultTransactionService implements TransactionService {
    private InternalTransactionApi transactionFetcher;
    private TransactionProcessor transactionProcessor;
    private TransactionCalculator transactionCalculator;

    public DefaultTransactionService(InternalTransactionApi transactionFetcher, TransactionCalculator transactionCalculator,
                                     TransactionProcessor transactionProcessor) {
        this.transactionFetcher = transactionFetcher;
        this.transactionCalculator = transactionCalculator;
        this.transactionProcessor = transactionProcessor;
    }

    @Override
    public <T> List<T> fetchTransactions(Class<T> responseType) {
        try {
            return transactionFetcher.fetchTransactions(responseType);
        } catch (RuntimeException e) {
            throw new ServiceException("Error while fetching transactions", e);
        }
    }

    @Override
    public <T> void processTransactions(List<T> transactions) {
        try {
            transactionProcessor.processTransactions((List<Transaction>) transactions);
        } catch (RuntimeException e) {
            throw new ServiceException("Error while processing transactions", e);
        }
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
