package com.nnk.springboot.services.implService;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.ITradeService;

/**
 * Service pour la gestion des trades.
 */
@Service
public class TradeServiceImpl implements ITradeService {
    
    private static final Logger logger = LogManager.getLogger(TradeServiceImpl.class);

    @Autowired
    private TradeRepository tradeRepository;
    
    /**
     * Récupère la liste de tous les trades.
     * @return liste des trades
     */
    public List<Trade> getAllTrade() {
        logger.info("Récupération de la liste des trades.");
        List<Trade> tradeList = tradeRepository.findAll();
        return tradeList;
    }
    
    /**
     * Récupère un trade par son ID.
     * @param id identifiant du trade
     * @return le trade correspondant
     */
    public Trade getTradeById(Integer id) {
        logger.info("Récupération du trade avec l'ID: {}", id);
        Trade trade = tradeRepository.findById(id).orElseThrow();
        logger.debug("Le contenu du Trade : {} ", trade);
        return trade;
    }
    
    /**
     * Ajoute un nouveau trade.
     * @param trade trade à ajouter
     * @return le trade ajouté
     */
    public Trade addTrade(Trade trade) {
        logger.info("Ajout d'un nouveau trade: {}", trade);
        return tradeRepository.save(trade);
    }
    
    /**
     * Met à jour un trade existant.
     * @param id identifiant du trade
     * @param newTrade nouvelles valeurs du trade
     * @return le trade mis à jour
     */
    public Trade updateTrade(Integer id, Trade newTrade) {
        logger.info("Mise à jour du trade avec l'ID: {}", id);
        Trade tradeToUpdate = getTradeById(id);
        tradeToUpdate.setAccount(newTrade.getAccount());
        tradeToUpdate.setBuyQuantity(newTrade.getBuyQuantity());
        tradeToUpdate.setType(newTrade.getType());
        return tradeRepository.save(tradeToUpdate);
    }
    
    /**
     * Supprime un trade par son ID.
     * @param id identifiant du trade
     */
    public void deleteTrade(Integer id) {
        logger.info("Suppression du trade avec l'ID: {}", id);
        Trade tradeToDelete = getTradeById(id);
        tradeRepository.delete(tradeToDelete);
    }
}
