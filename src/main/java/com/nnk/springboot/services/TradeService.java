package com.nnk.springboot.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

/**
 * Service pour la gestion des trades.
 */
@Service
public class TradeService {
    
    private static final Logger logger = LogManager.getLogger(TradeService.class);

    @Autowired
    private TradeRepository tradeRepository;
    
    /**
     * Récupère la liste de tous les trades.
     * @return liste des trades
     */
    public List<Trade> getAllTrade() {
        try {
            logger.info("Récupération de la liste des trades.");
            List<Trade> tradeList = tradeRepository.findAll();
            return tradeList;
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des trades", e);
            throw new RuntimeException("Erreur survenue lors de la récupération de la liste de Trade.", e);
        }
    }
    
    /**
     * Récupère un trade par son ID.
     * @param id identifiant du trade
     * @return le trade correspondant
     */
    public Trade getTradeById(Integer id) {
        try {
            logger.info("Récupération du trade avec l'ID: {}", id);
            return tradeRepository.findById(id).orElseThrow();
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération du trade avec l'ID: {}", id, e);
            throw new RuntimeException("Erreur survenue lors de la récupération du Trade.", e);
        }
    }
    
    /**
     * Ajoute un nouveau trade.
     * @param trade trade à ajouter
     * @return le trade ajouté
     */
    public Trade addTrade(Trade trade) {
        try {
            logger.info("Ajout d'un nouveau trade: {}", trade);
            return tradeRepository.save(trade);
        } catch (Exception e) {
            logger.error("Erreur lors de l'ajout du trade: {}", trade, e);
            throw new RuntimeException("Erreur survenue lors de l'ajout du Trade.", e);
        }
    }
    
    /**
     * Met à jour un trade existant.
     * @param id identifiant du trade
     * @param newTrade nouvelles valeurs du trade
     * @return le trade mis à jour
     */
    public Trade updateTrade(Integer id, Trade newTrade) {
        try {
            logger.info("Mise à jour du trade avec l'ID: {}", id);
            Trade tradeToUpdate = getTradeById(id);
            tradeToUpdate.setAccount(newTrade.getAccount());
            tradeToUpdate.setBuyQuantity(newTrade.getBuyQuantity());
            tradeToUpdate.setType(newTrade.getType());
            return tradeRepository.save(tradeToUpdate);
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour du trade avec l'ID: {}", id, e);
            throw new RuntimeException("Erreur survenue lors de la mise à jour du Trade.", e);
        }
    }
    
    /**
     * Supprime un trade par son ID.
     * @param id identifiant du trade
     */
    public void deleteTrade(Integer id) {
        try {
            logger.info("Suppression du trade avec l'ID: {}", id);
            Trade tradeToDelete = getTradeById(id);
            tradeRepository.delete(tradeToDelete);
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression du trade avec l'ID: {}", id, e);
            throw new RuntimeException("Erreur survenue lors de la suppression du Trade.", e);
        }
    }
}
