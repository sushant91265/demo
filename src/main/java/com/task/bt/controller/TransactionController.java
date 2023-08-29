package com.task.bt.controller;

import com.task.bt.model.BalanceResult;
import com.task.bt.service.TransactionService;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Slf4j
@RequestMapping("/api/v1")
public class TransactionController {
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(path = "/balances")
    public ResponseEntity<BalanceResult> getMonthlyBalances(@RequestParam(required = true) @Min(value = 1, message = "Invalid month")
                                    @Max(value = 12, message = "Invalid month") int month,
                                                            @RequestParam(required = true) @Digits(integer = 4, fraction = 0, message = "Invalid year")
                                    @Min(value = 1900, message = "Invalid year")
                                    @Max(value = 2999, message = "Invalid year")  int year) {
        return ResponseEntity.ok(transactionService.getBalances(month, year));
    }
}
