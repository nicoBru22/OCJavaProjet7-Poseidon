package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Rating;

/**
 * Interface de service pour la gestion des objets {@link Rating}.
 * Fournit les opérations CRUD pour les entités Rating.
 */
public interface IRatingService {

    /**
     * Récupère la liste complète des Ratings.
     * 
     * @return une liste de tous les objets Rating
     */
    List<Rating> getAllRating();

    /**
     * Ajoute un nouveau Rating.
     * 
     * @param rating l'objet Rating à ajouter
     * @return le Rating ajouté, généralement avec son identifiant généré
     */
    Rating addRating(Rating rating);

    /**
     * Récupère un Rating par son identifiant.
     * 
     * @param id l'identifiant du Rating recherché
     * @return le Rating correspondant à l'identifiant, ou {@code null} si non trouvé
     */
    Rating getRatingById(Integer id);

    /**
     * Met à jour un Rating existant identifié par son id.
     * 
     * @param id l'identifiant du Rating à mettre à jour
     * @param rating l'objet Rating contenant les nouvelles données
     * @return le Rating mis à jour, ou {@code null} si l'id n'existe pas
     */
    Rating updateRating(Integer id, Rating rating);

    /**
     * Supprime un Rating par son identifiant.
     * 
     * @param id l'identifiant du Rating à supprimer
     */
    void deleteRating(Integer id);
}
