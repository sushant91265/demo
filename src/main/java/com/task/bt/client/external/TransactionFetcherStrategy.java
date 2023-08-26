package com.task.bt.client.external;

import java.util.List;

public interface TransactionFetcherStrategy {
        <T> List<T> fetchTransactions(String url, int page, int size, Class<T> responseType);
}
