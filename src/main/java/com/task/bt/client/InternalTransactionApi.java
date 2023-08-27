package com.task.bt.client;

import java.util.Collection;
import java.util.List;

public interface InternalTransactionApi {
    <T> List<T> fetchTransactions(Class<T> responseType);
}

