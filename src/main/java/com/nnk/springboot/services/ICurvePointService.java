package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.CurvePoint;

/**
 * Interface de service pour la gestion des objets {@link CurvePoint}.
 * Fournit les opérations CRUD pour les entités CurvePoint.
 */
public interface ICurvePointService {

    /**
     * Récupère un CurvePoint par son identifiant.
     * 
     * @param id l'identifiant du CurvePoint recherché
     * @return le CurvePoint correspondant à l'identifiant, ou {@code null} si non trouvé
     */
    CurvePoint getCurvePointById(Integer id);

    /**
     * Récupère la liste complète des CurvePoints.
     * 
     * @return une liste de tous les objets CurvePoint
     */
    List<CurvePoint> getAllCurvePoint();

    /**
     * Ajoute un nouveau CurvePoint.
     * 
     * @param curvePoint l'objet CurvePoint à ajouter
     * @return le CurvePoint ajouté, généralement avec son identifiant généré
     */
    CurvePoint addCurvePoint(CurvePoint curvePoint);

    /**
     * Met à jour un CurvePoint existant identifié par son id.
     * 
     * @param id l'identifiant du CurvePoint à mettre à jour
     * @param curvePoint l'objet CurvePoint contenant les nouvelles données
     * @return le CurvePoint mis à jour, ou {@code null} si l'id n'existe pas
     */
    CurvePoint updateCurvePoint(Integer id, CurvePoint curvePoint);

    /**
     * Supprime un CurvePoint par son identifiant.
     * 
     * @param id l'identifiant du CurvePoint à supprimer
     */
    void deleteCurvePoint(Integer id);
}
