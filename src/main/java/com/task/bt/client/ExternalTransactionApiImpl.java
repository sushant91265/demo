package com.task.bt.client;

import com.task.bt.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ExternalTransactionApiImpl implements ExternalTransactionApi {
    @Override
    public List<Transaction> fetchTransactions() {
        // Call the actual external API here
        return Collections.unmodifiableList(new ArrayList<>());
    }
}
