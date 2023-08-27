package com.task.bt.calculator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Default implementation of {@link TransactionCalculator} interface.
 * This class is responsible for calculating monthly and cumulative balance.
 */
@Component
@Slf4j
public class DefaultTransactionCalculator implements TransactionCalculator {
    @Override
    public Double getMonthlyBalance(int month, int year) {
        //TODO: Logic to calculate monthly balance
        return null;
    }

    @Override
    public Double getCumulativeBalance(int endMonth, int endYear) {
        //TODO: Logic to calculate cumulative balance
        /*
        cumulative mhnje add karaycha e fkt..je pan dates yetil tyanla stream karun sum gheun takaycha
        split by year*month
        Cumulative Balance = Opening Balance + Debit - Credit
         */
        return null;
    }
}
