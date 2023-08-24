package com.task.bt.calculator;

import org.springframework.stereotype.Component;

@Component
public class DefaultTransactionCalculator implements TransactionCalculator {
    @Override
    public Double getMonthlyBalance(int month, int year) {
        //TODO: Logic to calculate monthly balance
        return null;
    }

    @Override
    public Double getCumulativeBalance(int endMonth, int endYear) {
        //TODO: Logic to calculate cumulative balance
        return null;
    }
}
