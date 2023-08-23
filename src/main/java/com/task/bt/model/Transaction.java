package com.task.bt.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Transaction {
    private Double amount;
    private LocalDate date;
}
