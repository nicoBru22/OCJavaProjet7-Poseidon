package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Trade;

/**
 * Interface de service pour la gestion des objets {@link Trade}.
 * Fournit les opérations CRUD pour les entités Trade.
 */
public interface ITradeService {

    /**
     * Récupère la liste complète des Trade.
     * 
     * @return une liste de tous les objets Trade
     */
    List<Trade> getAllTrade();

    /**
     * Récupère un Trade par son identifiant.
     * 
     * @param id l'identifiant du Trade recherché
     * @return le Trade correspondant à l'identifiant, ou {@code null} si non trouvé
     */
    Trade getTradeById(Integer id);

    /**
     * Ajoute un nouveau Trade.
     * 
     * @param trade l'objet Trade à ajouter
     * @return le Trade ajouté, généralement avec son identifiant généré
     */
    Trade addTrade(Trade trade);

    /**
     * Met à jour un Trade existant identifié par son id.
     * 
     * @param id l'identifiant du Trade à mettre à jour
     * @param newTrade l'objet Trade contenant les nouvelles données
     * @return le Trade mis à jour, ou {@code null} si l'id n'existe pas
     */
    Trade updateTrade(Integer id, Trade newTrade);

    /**
     * Supprime un Trade par son identifiant.
     * 
     * @param id l'identifiant du Trade à supprimer
     */
    void deleteTrade(Integer id);
}
