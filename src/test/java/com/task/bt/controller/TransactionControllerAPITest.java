package com.task.bt.controller;

import com.task.bt.client.external.TransactionFetcherStrategy;
import com.task.bt.model.BalanceResult;
import com.task.bt.service.TransactionService;
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

    @MockBean
    private TransactionFetcherStrategy transactionFetcherStrategy;

    private final String balancesUrlTemplate = "/api/v1/balances";
    private final String invalidMonthError = "Invalid month";
    private final String invalidYearError = "Invalid year";

    @Test
    public void testGetBalances() throws Exception {
        int month = 8;
        int year = 2023;

        BalanceResult mockBalanceResult = new BalanceResult(100.0, 200.0);

        when(transactionService.getBalances(month, year)).thenReturn(mockBalanceResult);

        mockMvc.perform(MockMvcRequestBuilders.get(balancesUrlTemplate)
                        .param("month", String.valueOf(month))
                        .param("year", String.valueOf(year)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.monthlyBalance").value(mockBalanceResult.getMonthlyBalance()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cumulativeBalance").value(mockBalanceResult.getCumulativeBalance()));
    }

    @Test
    public void testGetBalancesInvalidMonth() throws Exception {
        int month = -1;
        int year = 2022;

        mockMvc.perform(MockMvcRequestBuilders.get(balancesUrlTemplate)
                        .param("month", String.valueOf(month))
                        .param("year", String.valueOf(year)))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(invalidMonthError));
    }

    @Test
    public void testGetBalancesInvalidYear() throws Exception {
        int month = 8;
        int year = 202;

        mockMvc.perform(MockMvcRequestBuilders.get(balancesUrlTemplate)
                        .param("month", String.valueOf(month))
                        .param("year", String.valueOf(year)))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(invalidYearError));
    }

    @Test
    public void testGetBalancesWithNoYear() throws Exception {
        int month = 8;

        mockMvc.perform(MockMvcRequestBuilders.get(balancesUrlTemplate)
                        .param("month", String.valueOf(month)))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void testGetBalancesWithNoMonth() throws Exception {
        int year = 2023;

        mockMvc.perform(MockMvcRequestBuilders.get(balancesUrlTemplate)
                        .param("year", String.valueOf(year)))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void testGetBalancesWithBothIncorrectParams() throws Exception {
        int year = 20233;
        int month = -1;

        mockMvc.perform(MockMvcRequestBuilders.get(balancesUrlTemplate)
                        .param("month", String.valueOf(month))
                        .param("year", String.valueOf(year)))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void testGetBalancesWithoutAnyParam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(balancesUrlTemplate))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Test
    public void testBalancesAPIError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/balances/?abc=123"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    public void testAPIError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }
}