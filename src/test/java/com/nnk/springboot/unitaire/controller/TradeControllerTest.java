package com.nnk.springboot.unitaire.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.ITradeService;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class TradeControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ITradeService tradeService;
	
	@Test
	@WithMockUser(username="admin", roles="ADMIN")
	public void get_tradeList_page() throws Exception {
		List<Trade> tradeListTest = new ArrayList<>();
		
		when(tradeService.getAllTrade()).thenReturn(tradeListTest);
		
		mockMvc.perform(get("/trade/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("trade/list"));
		
		verify(tradeService, times(1)).getAllTrade();
	}
	
	@Test
	@WithMockUser(username="admin", roles="ADMIN")
	public void get_tradeAdd_page() throws Exception {		
		mockMvc.perform(get("/trade/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("trade/add"));
	}
	
	@Test
	@WithMockUser(username="admin", roles="ADMIN")
	public void get_tradeUpdate_page() throws Exception {	
		Trade tradeTest = new Trade();
		tradeTest.setTradeId(1);;
		
		when(tradeService.getTradeById(tradeTest.getTradeId())).thenReturn(tradeTest);
		
		mockMvc.perform(get("/trade/update/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("trade/update"));
		
		verify(tradeService, times(1)).getTradeById(tradeTest.getTradeId());
	}

}
