package com.task.bt.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Data
public class Transaction {
    private Double amount;
    private LocalDate date;

    public Transaction(Double amount, String date) {
        this.amount = amount;
        this.date = LocalDate.parse(date);
    }

    public int getMonth() {
        return date.getMonthValue();
    }

    public int getYear() {
        return date.getYear();
    }
}
