package com.task.bt.model;

import lombok.Data;

@Data
public class BalanceResult {

    private Double monthlyBalance, cumulativeBalance;
    public BalanceResult(Double monthlyBalance, Double cumulativeBalance) {
        this.monthlyBalance = monthlyBalance;
        this.cumulativeBalance = cumulativeBalance;
    }
}
