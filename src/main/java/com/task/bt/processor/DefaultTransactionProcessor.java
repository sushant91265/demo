package com.task.bt.processor;

import com.task.bt.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultTransactionProcessor implements TransactionProcessor {
    @Override
    public void processTransactions(List<Transaction> transactions) {
        //TODO: implement this method
    }
}
