package com.nnk.springboot.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@Service
public class RatingService {
	
	private Logger logger = LogManager.getLogger(RatingService.class);

	@Autowired
	private RatingRepository ratingRepository;
	
	public List<Rating> getAllRating() {
		logger.info("Entrée dans la méthode getAllRating de RatingService.");
		try {
			logger.info("Tentative de récupération de la liste de Rating.");
			List<Rating> ratingList = ratingRepository.findAll();
			logger.debug("Le contenu de la liste : {} ", ratingList);
			return ratingList;
		} catch (Exception e) {
			throw new RuntimeException("Une erreur est survenue lors de la récupération de la liste des Rating."+ e);
		}
	}
	
	public Rating addRating(Rating rating) {
		logger.info("Entrée dans la méthode addRating de RatingService.");
		if (rating == null) {
			logger.error("Le rating ne peut pas être nul.");
			throw new IllegalArgumentException("Le rating ne peut pas être nul.");
		}
		try {
			logger.info("Tentative d'ajout du nouveau rating : {} ", rating);
			Rating savedRating = ratingRepository.save(rating);
			logger.info("Le Rating a été ajouté avec succès : {} ", savedRating);
			
			return savedRating;
		} catch (Exception e) {
			logger.error("Une erreur est survenue lors de l'ajout de Rating.");
			throw new RuntimeException("Une erreur est survenue lors de l'ajout de Rating."+ e);
		}

	}
	
	public Rating getRatingById(Integer id) {
		logger.info("Entrée dans la méthode getRatingById de RatingService.");
		try {
			logger.info("Tentative de récupération de rating avec l'ID: {} ", id);
			Rating rating = ratingRepository.findById(id)
					.orElseThrow(() -> {
		                logger.warn("Aucun Rating trouvé pour l'ID : {}", id);
		                return new RuntimeException("BidList introuvable avec l'ID : " + id);
		            });
			return rating;
		} catch (Exception e) {
			logger.error("Une erreur est survenue lors de la récupération de Rating by ID.");
			throw new RuntimeException("Une erreur est survenue lors de la récupération de Rating by ID."+ e);
		}	
	}
	
	
	public Rating updateRating(Integer id, Rating rating) {
		logger.info("Entrée dans la méthode updateRating de RatingService.");
		try {
			Rating ratingToUpdate = getRatingById(id);
			logger.debug("Les modifications a apporter : {} ", rating);
			logger.info("Tentative de modification de : {} ", ratingToUpdate);

			ratingToUpdate.setFitchRating(rating.getFitchRating());
			ratingToUpdate.setMoodysRating(rating.getMoodysRating());
			ratingToUpdate.setOrderNumber(rating.getOrderNumber());
			ratingToUpdate.setSandPRating(rating.getSandPRating());
			
			logger.debug("Le rating modifié : {} ", ratingToUpdate);
			
			return ratingRepository.save(ratingToUpdate);
		} catch (Exception e) {
			logger.error("Une erreur est survenue lors de la modification de Rating avec l'ID : {} .", id);
			throw new RuntimeException("Une erreur est survenue lors de la modification de Rating."+ e);
		}
	}
	
	public void deleteRating(Integer id) {
		logger.info("Entrée dans la méthode deleteRating de RatingService.");
		try {
			Rating ratingToDelete = getRatingById(id);
			logger.debug("le rating à supprimer : {} ", ratingToDelete);
			logger.info("Tentative de suppression de : {} ", ratingToDelete);
			
			ratingRepository.delete(ratingToDelete);
			
			logger.info("Suppression de Rating réalisé avec succès.");
			
		} catch (Exception e) {
			logger.error("Une erreur est survenue lors de la suppression de Rating avec l'ID : {} .", id);
			throw new RuntimeException("Une erreur est survenue lors de la suppression de Rating."+ e);
		}
	}
}
