package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.BidList;

/**
 * Interface de service pour la gestion des objets {@link BidList}.
 * Fournit les opérations CRUD pour les entités BidList.
 */
public interface IBidListService {

    /**
     * Récupère la liste complète des BidList.
     * 
     * @return une liste de tous les objets BidList
     */
    List<BidList> getAllBid();

    /**
     * Récupère un BidList par son identifiant.
     * 
     * @param id l'identifiant du BidList recherché
     * @return le BidList correspondant à l'identifiant, ou {@code null} si non trouvé
     */
    BidList getBidById(Integer id);

    /**
     * Ajoute un nouveau BidList.
     * 
     * @param bid l'objet BidList à ajouter
     * @return le BidList ajouté, généralement avec son identifiant généré
     */
    BidList addBid(BidList bid);

    /**
     * Met à jour un BidList existant identifié par son id.
     * 
     * @param id l'identifiant du BidList à mettre à jour
     * @param bidList l'objet BidList contenant les nouvelles données
     * @return le BidList mis à jour, ou {@code null} si l'id n'existe pas
     */
    BidList updateBidList(Integer id, BidList bidList);

    /**
     * Supprime un BidList par son identifiant.
     * 
     * @param id l'identifiant du BidList à supprimer
     */
    void deleteBidList(Integer id);
}
