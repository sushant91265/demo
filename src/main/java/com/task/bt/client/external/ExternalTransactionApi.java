package com.task.bt.client.external;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ExternalTransactionApi {

    <T> CompletableFuture<List<T>> fetchTransactions(String url, int page, int size, Class<T> responseType);

}
