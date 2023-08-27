package com.task.bt;

import com.task.bt.controller.TransactionController;
import com.task.bt.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(TransactionController.class)
public class TransactionControllerAPITest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TransactionService transactionService;

    private final String monthlyBalanceUrlTemplate = "/api/v1/balances/monthly-balance";
    private final String cumulativeBalanceUrlTemplate = "/api/v1/balances/cumulative-balance";
    private final String invalidMonthError = "Invalid month";
    private final String invalidYearError = "Invalid year";

    @Test
    public void testGetMonthlyBalance() throws Exception {
        int month = 8;
        int year = 2023;
        double mockBalance = 5000.0;

        when(transactionService.getMonthlyBalance(month, year)).thenReturn(mockBalance);

        mockMvc.perform(MockMvcRequestBuilders.get(monthlyBalanceUrlTemplate)
                        .param("month", String.valueOf(month))
                        .param("year", String.valueOf(year)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(mockBalance));
    }

    @Test
    public void testGetMonthlyBalanceInvalidMonth() throws Exception {
        int month = -1;
        int year = 2022;

        mockMvc.perform(MockMvcRequestBuilders.get(monthlyBalanceUrlTemplate)
                        .param("month", String.valueOf(month))
                        .param("year", String.valueOf(year)))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(invalidMonthError));
    }

    @Test
    public void testGetMonthlyBalanceInvalidYear() throws Exception {
        int month = 8;
        int year = 202;

        mockMvc.perform(MockMvcRequestBuilders.get(monthlyBalanceUrlTemplate)
                        .param("month", String.valueOf(month))
                        .param("year", String.valueOf(year)))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(invalidYearError));
    }

    @Test
    void testGetCumulativeBalance() throws Exception {
        int endMonth = 8;
        int endYear = 2023;
        double mockBalance = 5000.0;

        when(transactionService.getCumulativeBalance(endMonth, endYear)).thenReturn(mockBalance);

        mockMvc.perform(MockMvcRequestBuilders.get(cumulativeBalanceUrlTemplate)
                        .param("endMonth", String.valueOf(endMonth))
                        .param("endYear", String.valueOf(endYear)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(mockBalance));
    }

    @Test
    public void testGetCumulativeBalanceInvalidMonth() throws Exception {
        int endMonth = -1;
        int endYear = 2022;

        mockMvc.perform(MockMvcRequestBuilders.get(cumulativeBalanceUrlTemplate)
                        .param("endMonth", String.valueOf(endMonth))
                        .param("endYear", String.valueOf(endYear)))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(invalidMonthError));
    }

    @Test
    public void testGetCumulativeBalanceInvalidYear() throws Exception {
        int endMonth = 8;
        int endYear = 202;

        mockMvc.perform(MockMvcRequestBuilders.get(cumulativeBalanceUrlTemplate)
                        .param("endMonth", String.valueOf(endMonth))
                        .param("endYear", String.valueOf(endYear)))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(invalidYearError));
    }

    @Test
    public void testGetCumulativeBalanceWithNoYear() throws Exception {
        int endMonth = 8;

        mockMvc.perform(MockMvcRequestBuilders.get(cumulativeBalanceUrlTemplate)
                        .param("endMonth", String.valueOf(endMonth)))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void testGetCumulativeBalanceWithNoMonth() throws Exception {
        int endYear = 2023;

        mockMvc.perform(MockMvcRequestBuilders.get(cumulativeBalanceUrlTemplate)
                        .param("endYear", String.valueOf(endYear)))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void testGetCumulativeBalanceWithoutAnyParam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(cumulativeBalanceUrlTemplate))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void testGetMonthlyBalanceWithNoYear() throws Exception {
        int endMonth = 8;

        mockMvc.perform(MockMvcRequestBuilders.get(monthlyBalanceUrlTemplate)
                        .param("month", String.valueOf(endMonth)))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void testGetMonthlyBalanceWithNoMonth() throws Exception {
        int endYear = 2023;

        mockMvc.perform(MockMvcRequestBuilders.get(monthlyBalanceUrlTemplate)
                        .param("year", String.valueOf(endYear)))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void testGetMonthlyBalanceWithoutAnyParam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(monthlyBalanceUrlTemplate))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void testBalancesAPIError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/balances"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    public void testAPIError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }
}