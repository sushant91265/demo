package com.task.bt.controller;

import com.task.bt.service.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/balances")
public class TransactionController {
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(path = "/monthly-balance", produces = "text/plain")
    public Double getMonthlyBalance(@RequestParam int month, @RequestParam int year) {
        return transactionService.getMonthlyBalance(month, year);
    }

    @GetMapping(path = "/cumulative-balance", produces = "text/plain")
    public Double getCumulativeBalance(@RequestParam int endMonth, @RequestParam int endYear) {
        return transactionService.getCumulativeBalance(endMonth, endYear);
    }
}
