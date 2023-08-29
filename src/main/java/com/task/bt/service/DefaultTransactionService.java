package com.task.bt.service;

import com.task.bt.client.InternalTransactionApi;
import com.task.bt.exception.InternalApiException;
import com.task.bt.exception.ServiceException;
import com.task.bt.model.BalanceResult;
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
    public BalanceResult getBalances(int month, int year) {
        List<Transaction> transactions = fetchTransactions(Transaction.class);

        Double monthlyBalance = calculateMonthlyBalance(transactions, month, year);
        Double cumulativeBalance = calculateCumulativeBalance(transactions, year);

        return new BalanceResult(monthlyBalance, cumulativeBalance);
    }


    private Double calculateMonthlyBalance(List<Transaction> transactions, int month, int year) {
        Predicate<Transaction> filter = txn -> txn.getMonth() == month && txn.getYear() == year;
        return transactionProcessor.calculateSum(transactions, filter);
    }

    private Double calculateCumulativeBalance(List<Transaction> transactions, int endYear) {
        // calculate cumulative balance from start of the year till endYear
        Predicate<Transaction> filter = txn -> txn.getYear() == endYear;
        return transactionProcessor.calculateSum(transactions, filter);
    }

    /*
    * If required transactions can be saved in database and processed later; depending on how much
    * processing we need to do.
    * */

    private <T> List<T> fetchTransactions(Class<T> responseType) {
        try {
            return transactionFetcher.fetchTransactions(responseType);
        } catch (InternalApiException internalApiException) {
            throw new ServiceException("Error while fetching transactions", internalApiException);
        }
    }
}
