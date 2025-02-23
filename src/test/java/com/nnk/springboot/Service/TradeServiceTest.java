package com.nnk.springboot.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;

@SpringBootTest
@AutoConfigureMockMvc
public class TradeServiceTest {
	
	@Autowired
	private TradeService tradeService;
	
	@Mock
	private TradeRepository tradeRepository;
	
	@Test
	public void getAllTradeTest() {
		Trade trade1 = new Trade();
		trade1.setAccount("account1Test");
		Trade trade2 = new Trade();
		List<Trade> tradeListTest = new ArrayList<>();
		tradeListTest.add(trade1);
		tradeListTest.add(trade2);
		
		when(tradeRepository.findAll()).thenReturn(tradeListTest);
		
		List<Trade> tradeListExpected = tradeService.getAllTrade();
		
		verify(tradeRepository, times(1)).findAll();
        assertThat(tradeListExpected).isEqualTo(tradeListTest);		
	}

}
