package com.task.bt.controller;

import com.task.bt.service.TransactionService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.mockito.Mockito.when;

@WebMvcTest(TransactionController.class)
public class TransactionControllerAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    @Disabled
    public void testGetMonthlyBalance() throws Exception {
        int month = 8;
        int year = 2023;
        double mockBalance = 5000.0;

        when(transactionService.getMonthlyBalance(month, year)).thenReturn(mockBalance);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/monthly-balance")
                        .param("month", String.valueOf(month))
                        .param("year", String.valueOf(year)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"balance\":5000.0}"));
    }

    @Test
    @Disabled
    public void testGetMonthlyBalanceInvalidInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/monthly-balance")
                        .param("month", "13") // Invalid month
                        .param("year", "2023"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/monthly-balance")
                        .param("month", "8")
                        .param("year", "invalid")) // Invalid year
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testGetMonthlyBalanceNotFound() throws Exception {
        int month = 8;
        int year = 20222;

        when(transactionService.getMonthlyBalance(month, year)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/monthly-balance")
                        .param("month", String.valueOf(month))
                        .param("year", String.valueOf(year)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}