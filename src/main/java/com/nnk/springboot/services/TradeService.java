package com.nnk.springboot.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

@Service
public class TradeService {
	
	private Logger logger = LogManager.getLogger(TradeService.class);

	@Autowired
	private TradeRepository tradeRepository;
	
	public List<Trade> getAllTrade() {
		try {
			List<Trade> tradeList = tradeRepository.findAll();
			return tradeList;
		} catch (Exception e) {
			throw new RuntimeException("Erreur survenue lors de la récupération de la liste de Trade." + e);
		}
	}
	
	public Trade getTradeById(Integer id) {
		try {
			Trade trade = tradeRepository.findById(id)
					.orElseThrow();
			return trade;
		} catch (Exception e) {
			throw new RuntimeException("Erreur survenue lors de la récupération du Trade." + e);

		}
	}
	
	public Trade addTrade(Trade trade) {
		try {
			return tradeRepository.save(trade);
		}catch (Exception e) {
			throw new RuntimeException("Erreur survenue lors d'ajout du Trade." + e);
		}
	}
	
	public Trade updateTrade(Integer id, Trade newTrade) {
		try {
			Trade tradeToUpdate = getTradeById(id);
			tradeToUpdate.setAccount(newTrade.getAccount());
			tradeToUpdate.setBuyQuantity(newTrade.getBuyQuantity());
			tradeToUpdate.setType(newTrade.getType());
			
			return tradeRepository.save(tradeToUpdate);
			
		}catch (Exception e) {
			throw new RuntimeException("Erreur survenue lors de la récupération du Trade." + e);
		}
	}
	
	public void deleteTrade(Integer id) {
		try {
			Trade tradeToDelete = getTradeById(id);
			tradeRepository.delete(tradeToDelete);
		} catch (Exception e) {
			throw new RuntimeException("Erreur survenue lors de la suppression du Trade." + e);
		}
	}
	
}
