package com.task.bt.model;

import lombok.Data;

@Data
public class BalanceResult {

    Double monthlyBalance, cumulativeBalance;
    public BalanceResult(Double monthlyBalance, Double cumulativeBalance) {
        this.monthlyBalance = monthlyBalance;
        this.cumulativeBalance = cumulativeBalance;
    }

    public double getMonthlyBalance() {
        return monthlyBalance;
    }

    public double getCumulativeBalance() {
        return cumulativeBalance;
    }
}
