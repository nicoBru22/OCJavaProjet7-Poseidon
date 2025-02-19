package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@Service
public class BidListService {
	
	private Logger logger = LogManager.getLogger(BidListService.class);
	
	@Autowired
	private BidListRepository bidListRepository;

	public List<BidList> getAllBid() throws Exception {
		logger.info("Entrée dans la méthode getAllBid().");
		try {
			logger.info("Appel de la méthode bidListRepository.findAll()");
			List<BidList> bidList = bidListRepository.findAll();

			if(bidList.isEmpty() || bidList == null) {
				logger.debug("La bidList est vide ou nulle : {} ", bidList);
			} else {
				logger.info("Ce que contient la bidList : {} ", bidList);
			}
			logger.info("Je susi là. ");
			return bidList;
		} catch (Exception e) {
			logger.error("Erreur lors de la récupération de la liste de Bid.", e);
			throw new RuntimeException("Erreur lors de la récupération de la liste de Bid.", e);
		}
	}
	
	public BidList getBidById(Integer id) {
	    logger.info("Entrée dans la méthode getBidById() avec ID : {}", id);
	    if (id == null) {
	        logger.error("L'ID fourni est nul.");
	        throw new IllegalArgumentException("L'ID ne peut pas être nul.");
	    }
	    try {
		    BidList actualBid = bidListRepository.findById(id)
		            .orElseThrow(() -> {
		                logger.warn("Aucun BidList trouvé pour l'ID : {}", id);
		                return new RuntimeException("BidList introuvable avec l'ID : " + id);
		            });
		    return actualBid;
	    } catch (Exception e) {
			logger.error("Erreur lors de l'ajout d'un nouveau BidList.", e);
			throw new RuntimeException("Erreur lors de l'ajout d'un nouveau BidList.", e);
	    }
	}


	
	public BidList addBid(BidList bid) {
		logger.info("Entrée dans la méthode addBid().");
		try {
			if(bid == null) {
				throw new IllegalArgumentException("Le bid à ajouter ne peut pas être nul.");
			}
			logger.info("Appel de la méthode bidListRepository.save()");
			logger.debug("Le nouveau bid à ajouter est : {} ", bid);
			bidListRepository.save(bid);
			logger.info("Le bid a été ajouté avec succès.");
			return bid;
		} catch (Exception e) {
			logger.error("Erreur lors de l'ajout d'un nouveau BidList.", e);
			throw new RuntimeException("Erreur lors de l'ajout d'un nouveau BidList.", e);
		}
		
	}
	
	public BidList updateBidList(Integer id, BidList bidList) {
	    logger.info("Entrée dans la méthode updateBidList() pour l'ID {}", id);
	    try {
	    	logger.info("Tentative de récupération de bid avec l'id {}", id);
		    BidList bidToUpdate = getBidById(id);

		    bidToUpdate.setAccount(bidList.getAccount());
		    bidToUpdate.setType(bidList.getType());
		    bidToUpdate.setBidQuantity(bidList.getBidQuantity());

		    logger.info("Mise à jour du bid avec ID {}", id);
		    logger.debug("Nouveau contenu du bid : {}", bidToUpdate);

		    return bidListRepository.save(bidToUpdate);
	    } catch (Exception e) {
			logger.error("Erreur lors de la mise à jour du Bid.", e);
			throw new RuntimeException("Erreur lors de la mise à jour du Bid.", e);
	    }

	}
	
	public void deleteBidList(Integer id) {
	    logger.info("Entrée dans la méthode deleteBidList() pour l'ID {}", id);
		try {
			BidList bidToDelete = getBidById(id);
			bidListRepository.delete(bidToDelete);
		} catch (Exception e) {
			logger.error("Erreur lors de la suppression du Bid.", e);
			throw new RuntimeException("erreur lors de la suppréssion du Bid : {} " + id);
		}

	}

}
