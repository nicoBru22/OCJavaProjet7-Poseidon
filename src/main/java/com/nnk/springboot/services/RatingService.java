package com.nnk.springboot.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

/**
 * Service gérant les opérations sur les objets Rating.
 */
@Service
public class RatingService {

    private static final Logger logger = LogManager.getLogger(RatingService.class);

    @Autowired
    private RatingRepository ratingRepository;

    /**
     * Récupère tous les Rating disponibles dans la base de données.
     * 
     * @return une liste de Rating
     * @throws RuntimeException en cas d'erreur lors de la récupération des données
     */
    public List<Rating> getAllRating() {
        logger.info("Entrée dans la méthode getAllRating.");
        try {
            List<Rating> ratingList = ratingRepository.findAll();
            logger.debug("Liste des Ratings récupérée : {}", ratingList);
            return ratingList;
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des Ratings", e);
            throw new RuntimeException("Une erreur est survenue lors de la récupération des Ratings.", e);
        }
    }

    /**
     * Ajoute un nouveau Rating à la base de données.
     * 
     * @param rating le Rating à ajouter
     * @return le Rating ajouté
     * @throws IllegalArgumentException si le Rating est null
     * @throws RuntimeException en cas d'erreur lors de l'ajout
     */
    public Rating addRating(Rating rating) {
        logger.info("Entrée dans la méthode addRating.");
        if (rating == null) {
            logger.error("Le rating ne peut pas être null.");
            throw new IllegalArgumentException("Le rating ne peut pas être null.");
        }
        try {
            Rating savedRating = ratingRepository.save(rating);
            logger.info("Rating ajouté avec succès : {}", savedRating);
            return savedRating;
        } catch (Exception e) {
            logger.error("Erreur lors de l'ajout du Rating", e);
            throw new RuntimeException("Une erreur est survenue lors de l'ajout du Rating.", e);
        }
    }

    /**
     * Récupère un Rating par son ID.
     * 
     * @param id l'ID du Rating
     * @return le Rating correspondant
     * @throws RuntimeException si aucun Rating n'est trouvé
     */
    public Rating getRatingById(Integer id) {
        logger.info("Entrée dans la méthode getRatingById pour l'ID : {}", id);
        try {
            return ratingRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Aucun Rating trouvé pour l'ID : {}", id);
                    return new RuntimeException("Rating introuvable avec l'ID : " + id);
                });
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération du Rating par ID", e);
            throw new RuntimeException("Une erreur est survenue lors de la récupération du Rating par ID.", e);
        }
    }

    /**
     * Met à jour un Rating existant.
     * 
     * @param id l'ID du Rating à mettre à jour
     * @param rating les nouvelles valeurs du Rating
     * @return le Rating mis à jour
     * @throws RuntimeException en cas d'erreur lors de la mise à jour
     */
    public Rating updateRating(Integer id, Rating rating) {
        logger.info("Entrée dans la méthode updateRating pour l'ID : {}", id);
        try {
            Rating ratingToUpdate = getRatingById(id);
            logger.debug("Modifications à apporter : {}", rating);
            
            ratingToUpdate.setFitchRating(rating.getFitchRating());
            ratingToUpdate.setMoodysRating(rating.getMoodysRating());
            ratingToUpdate.setOrderNumber(rating.getOrderNumber());
            ratingToUpdate.setSandPRating(rating.getSandPRating());
            
            Rating updatedRating = ratingRepository.save(ratingToUpdate);
            logger.info("Rating mis à jour avec succès : {}", updatedRating);
            return updatedRating;
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour du Rating avec l'ID : {}", id, e);
            throw new RuntimeException("Une erreur est survenue lors de la mise à jour du Rating.", e);
        }
    }

    /**
     * Supprime un Rating par son ID.
     * 
     * @param id l'ID du Rating à supprimer
     * @throws RuntimeException en cas d'erreur lors de la suppression
     */
    public void deleteRating(Integer id) {
        logger.info("Entrée dans la méthode deleteRating pour l'ID : {}", id);
        try {
            Rating ratingToDelete = getRatingById(id);
            logger.debug("Rating à supprimer : {}", ratingToDelete);
            ratingRepository.delete(ratingToDelete);
            logger.info("Suppression du Rating réalisée avec succès.");
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression du Rating avec l'ID : {}", id, e);
            throw new RuntimeException("Une erreur est survenue lors de la suppression du Rating.", e);
        }
    }
}

