package com.task.bt.controller;

import com.task.bt.service.TransactionService;
import jakarta.servlet.ServletException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertThrows;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(TransactionController.class)
public class TransactionControllerAPITest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TransactionService transactionService;

    @Test
    public void testGetMonthlyBalance() throws Exception {
        int month = 8;
        int year = 2023;
        double mockBalance = 5000.0;

        when(transactionService.getMonthlyBalance(month, year)).thenReturn(mockBalance);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/balances/monthly-balance")
                        .param("month", String.valueOf(month))
                        .param("year", String.valueOf(year)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(5000));
    }

    @Test
    public void testGetMonthlyBalanceInvalidMonth() {
        ServletException exception = assertThrows(ServletException.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/balances/monthly-balance")
                            .param("month", "13")
                            .param("year", "2023"))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        });
        Throwable rootCause = exception.getRootCause();
        assert rootCause instanceof ConstraintViolationException;
    }

    @Test
    public void testGetMonthlyBalanceInvalidYear() {
        ServletException exception = assertThrows(ServletException.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/balances/monthly-balance")
                            .param("month", "13")
                            .param("year", "20232"));
        });

        Throwable rootCause = exception.getRootCause();
        assert rootCause instanceof ConstraintViolationException;
    }

    @Test
    @Disabled
    public void testGetMonthlyBalanceNotFound() throws Exception {
        int month = 8;
        int year = 2022;

        when(transactionService.getMonthlyBalance(month, year)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/balances/monthly-balance")
                        .param("month", String.valueOf(month))
                        .param("year", String.valueOf(year)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}