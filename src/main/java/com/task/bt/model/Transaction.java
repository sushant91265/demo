package com.task.bt.model;

import lombok.Data;

import java.util.Date;

@Data
public class Transaction {
    private Double amount;
    private Date date;
}
