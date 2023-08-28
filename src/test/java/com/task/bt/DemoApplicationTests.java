package com.task.bt;

import com.task.bt.client.external.TransactionFetcherStrategy;
import com.task.bt.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.awaitility.Awaitility.await;
import static org.awaitility.Durations.ONE_SECOND;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@MockBean(name = "paginatedExternalTransactionApi")
	private TransactionFetcherStrategy transactionFetcherStrategy;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetMonthlyBalanceE2E() {
		int month = 9;
		int year = 2023;
		double mockBalance = 210.0;

		List<Transaction> mockTransactions = List.of(new Transaction(100.0,"2023-09-09"),
													 new Transaction(110.0,"2023-09-15"));

		when(transactionFetcherStrategy.fetchTransactions(anyString(), anyInt(), anyInt(), any(Class.class))).thenReturn(mockTransactions);

		await().atMost(ONE_SECOND).untilAsserted(() -> {
			mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/balances/monthly-balance")
							.param("month", String.valueOf(month))
							.param("year", String.valueOf(year))
							.contentType("application/json"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(mockBalance));
		});
	}

	@Test
	public void testGetCumulativeBalanceE2E() {
		int month = 9;
		int year = 2023;
		double mockBalance = 210.0;

		List<Transaction> mockTransactions = List.of(new Transaction(100.0,"2023-09-09"),
				new Transaction(110.0,"2023-09-15"));

		when(transactionFetcherStrategy.fetchTransactions(anyString(), anyInt(), anyInt(), any(Class.class))).thenReturn(mockTransactions);

		await().atMost(ONE_SECOND).untilAsserted(() -> {
			mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/balances/cumulative-balance")
							.param("endMonth", String.valueOf(month))
							.param("endYear", String.valueOf(year))
							.contentType("application/json"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(mockBalance));
		});
	}
}
