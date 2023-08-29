package com.task.bt.service;

import com.task.bt.model.BalanceResult;

public interface TransactionService {
    <T> T getBalances(int month, int year);
}
