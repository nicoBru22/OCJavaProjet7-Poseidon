package com.nnk.springboot.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
public class Test_integration_trade {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TradeService tradeService;
	
	@Test
	@Transactional
	@WithMockUser(username="admin", roles="ADMIN")
	public void addTrade_test() throws Exception {
		Trade tradeTest = new Trade();
		tradeTest.setAccount("account test");
		tradeTest.setType("type test");
		tradeTest.setBuyQuantity(15.50);
		
		mockMvc.perform(post("/trade/validate")
				.param("account", tradeTest.getAccount())
				.param("type", tradeTest.getType())
				.param("buyQuantity", tradeTest.getBuyQuantity().toString()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/trade/list"));
		
		List<Trade> tradeList = tradeService.getAllTrade();
		Optional<Trade> savedTrade = tradeList.stream()
				.filter(s ->
					s.getAccount().equals(tradeTest.getAccount()) &&
					s.getType().equals(tradeTest.getType()) &&
					s.getBuyQuantity().equals(tradeTest.getBuyQuantity()))
				.findFirst();
		
		assertThat(savedTrade).isPresent();
	}
	
	@Test
	@Transactional
	@WithMockUser(username="admin", roles="ADMIN")
	public void deleteTrade_test() throws Exception {
		Trade tradeTest = new Trade();
		tradeTest.setAccount("account test");
		tradeTest.setType("type test");
		tradeTest.setBuyQuantity(15.50);
		
		mockMvc.perform(post("/trade/validate")
				.param("account", tradeTest.getAccount())
				.param("type", tradeTest.getType())
				.param("buyQuantity", tradeTest.getBuyQuantity().toString()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/trade/list"));
		
		List<Trade> tradeList = tradeService.getAllTrade();
		Optional<Trade> savedTrade = tradeList.stream()
				.filter(s ->
					s.getAccount().equals(tradeTest.getAccount()) &&
					s.getType().equals(tradeTest.getType()) &&
					s.getBuyQuantity().equals(tradeTest.getBuyQuantity()))
				.findFirst();
		
		assertThat(savedTrade).isPresent();
		
		Integer tradeID = savedTrade.get().getTradeId();
		
		mockMvc.perform(get("/trade/delete/" + tradeID))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/trade/list"));
		
		List<Trade> tradeListDelete = tradeService.getAllTrade();
		Optional<Trade> deletedTrade = tradeListDelete.stream()
				.filter(s ->
					s.getAccount().equals(tradeTest.getAccount()) &&
					s.getType().equals(tradeTest.getType()) &&
					s.getBuyQuantity().equals(tradeTest.getBuyQuantity()))
				.findFirst();
		
		assertThat(deletedTrade).isEmpty();
		
	}
	
	@Test
	@Transactional
	@WithMockUser(username="admin", roles="ADMIN")
	public void updateTrade_test() throws Exception {
		Trade tradeTest = new Trade();
		tradeTest.setAccount("account test");
		tradeTest.setType("type test");
		tradeTest.setBuyQuantity(15.50);
		
		Trade tradeTestUpdate = new Trade();
		tradeTestUpdate.setAccount("account test 2");
		tradeTestUpdate.setType("type test 2");
		tradeTestUpdate.setBuyQuantity(12.34);
		
		mockMvc.perform(post("/trade/validate")
				.param("account", tradeTest.getAccount())
				.param("type", tradeTest.getType())
				.param("buyQuantity", tradeTest.getBuyQuantity().toString()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/trade/list"));
		
		List<Trade> tradeList = tradeService.getAllTrade();
		Optional<Trade> savedTrade = tradeList.stream()
				.filter(s ->
					s.getAccount().equals(tradeTest.getAccount()) &&
					s.getType().equals(tradeTest.getType()) &&
					s.getBuyQuantity().equals(tradeTest.getBuyQuantity()))
				.findFirst();
		
		assertThat(savedTrade).isPresent();
		
		Integer tradeID = savedTrade.get().getTradeId();
		
		mockMvc.perform(post("/trade/update/" + tradeID)
				.param("account", tradeTestUpdate.getAccount())
				.param("type", tradeTestUpdate.getType())
				.param("buyQuantity", tradeTestUpdate.getBuyQuantity().toString()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/trade/list"));
		
		List<Trade> tradeListUpdate = tradeService.getAllTrade();
		Optional<Trade> updatedTrade = tradeListUpdate.stream()
				.filter(s ->
					s.getAccount().equals(tradeTestUpdate.getAccount()) &&
					s.getType().equals(tradeTestUpdate.getType()) &&
					s.getBuyQuantity().equals(tradeTestUpdate.getBuyQuantity()))
				.findFirst();
		
		assertThat(updatedTrade).isPresent();
		
		
	}

}
