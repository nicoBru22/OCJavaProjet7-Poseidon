package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Trade;

public interface ITradeService {
	List<Trade> getAllTrade();
	Trade getTradeById(Integer id);
	Trade addTrade(Trade trade);
	Trade updateTrade(Integer id, Trade newTrade);
	void deleteTrade(Integer id);
}
