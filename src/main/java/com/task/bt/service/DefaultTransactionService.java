package com.task.bt.service;

import com.task.bt.client.InternalTransactionApi;
import com.task.bt.exception.InternalApiException;
import com.task.bt.exception.ServiceException;
import com.task.bt.model.Transaction;
import com.task.bt.processor.TransactionProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

/**
 * Implementation of {@link TransactionService} interface.
 * This class is responsible for handling service layer.
 */

@Service
@Slf4j
public class DefaultTransactionService implements TransactionService {
    private final InternalTransactionApi transactionFetcher;
    private final TransactionProcessor transactionProcessor;

    public DefaultTransactionService(InternalTransactionApi transactionFetcher, TransactionProcessor transactionProcessor) {
        this.transactionFetcher = transactionFetcher;
        this.transactionProcessor = transactionProcessor;
    }

    @Override
    public Double getMonthlyBalance(int month, int year) {
        List<Transaction> transactions = fetchTransactions(Transaction.class);
        Predicate<Transaction> filter = txn -> txn.getMonth() == month && txn.getYear() == year;
        return transactionProcessor.calculateSum(transactions, filter);
    }

    @Override
    public Double getCumulativeBalance(int endMonth, int endYear) {
        List<Transaction> transactions = fetchTransactions(Transaction.class);
        return transactionProcessor.calculateSum(transactions,
                txn -> {
                    if(txn.getYear() == endYear) return txn.getMonth() <= endMonth;
                    return txn.getYear() <= endYear;
        });
    }

    protected <T> List<T> fetchTransactions(Class<T> responseType) {
        try {
            return transactionFetcher.fetchTransactions(responseType);
        } catch (InternalApiException internalApiException) {
            throw new ServiceException("Error while fetching transactions", internalApiException);
        }
    }
}
