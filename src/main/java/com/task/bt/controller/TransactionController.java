package com.task.bt.controller;

import com.task.bt.service.TransactionService;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@Slf4j
@RequestMapping("/api/balances")
public class TransactionController {
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(path = "/monthly-balance")
    public Double getMonthlyBalance(@RequestParam @Min(1) @Max(12) @NonNull int month,
                                    @RequestParam @Digits(integer = 4, fraction = 0) @NonNull int year) {
        return transactionService.getMonthlyBalance(month, year);
    }

    @GetMapping(path = "/cumulative-balance")
    public Double getCumulativeBalance(@RequestParam @Min(1) @Max(12) int endMonth,
                                       @RequestParam @Digits(integer = 4, fraction = 0) int endYear) {
        return transactionService.getCumulativeBalance(endMonth, endYear);
    }
}
