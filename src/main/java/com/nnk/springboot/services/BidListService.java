package com.nnk.springboot.services;

import java.util.List;

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
}
