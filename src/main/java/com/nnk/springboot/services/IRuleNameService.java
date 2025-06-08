package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.RuleName;

/**
 * Interface de service pour la gestion des objets {@link RuleName}.
 * Fournit les opérations CRUD pour les entités RuleName.
 */
public interface IRuleNameService {

    /**
     * Récupère la liste complète des RuleName.
     * 
     * @return une liste de tous les objets RuleName
     */
    List<RuleName> getAllRuleName();

    /**
     * Récupère un RuleName par son identifiant.
     * 
     * @param id l'identifiant du RuleName recherché
     * @return le RuleName correspondant à l'identifiant, ou {@code null} si non trouvé
     */
    RuleName getRuleNameById(Integer id);

    /**
     * Ajoute un nouveau RuleName.
     * 
     * @param ruleName l'objet RuleName à ajouter
     * @return le RuleName ajouté, généralement avec son identifiant généré
     */
    RuleName addRuleName(RuleName ruleName);

    /**
     * Supprime un RuleName par son identifiant.
     * 
     * @param id l'identifiant du RuleName à supprimer
     */
    void deleteRuleName(Integer id);

    /**
     * Met à jour un RuleName existant identifié par son id.
     * 
     * @param id l'identifiant du RuleName à mettre à jour
     * @param ruleName l'objet RuleName contenant les nouvelles données
     * @return le RuleName mis à jour, ou {@code null} si l'id n'existe pas
     */
    RuleName updateRuleName(Integer id, RuleName ruleName);  
}
