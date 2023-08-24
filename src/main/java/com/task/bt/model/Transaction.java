package com.task.bt.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Transaction {
    private Double amount;
    private LocalDate date;

    public Transaction(double amt, String date) {
        this.amount = amt;
        this.date = LocalDate.parse(date);
    }
}
