package com.task.bt.model;

import lombok.Data;

@Data
public class Balance {
    private Double balance;

    public Balance(double balance) {
        this.balance = balance;
    }
}
