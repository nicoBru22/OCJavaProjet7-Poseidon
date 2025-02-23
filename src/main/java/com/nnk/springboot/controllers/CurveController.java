package com.nnk.springboot.controllers;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;

import jakarta.validation.Valid;

/**
 * Contrôleur pour gérer les entités CurvePoint.
 * Cette classe gère les opérations CRUD pour les CurvePoints et interagit avec le service CurvePointService.
 */
@Controller
public class CurveController {

    private static final Logger logger = LogManager.getLogger(CurveController.class);

    @Autowired
    private CurvePointService curvePointService;

    /**
     * Affiche la liste de tous les CurvePoints.
     * 
     * @param model Le modèle pour ajouter les attributs.
     * @return Le nom de la vue pour la liste des CurvePoints.
     */
    @GetMapping("/curvePoint/list")
    public String home(Model model) {
        try {
            logger.info("Récupération de tous les CurvePoints pour l'affichage de la liste.");
            List<CurvePoint> curvePoints = curvePointService.getAllCurvePoint();
            model.addAttribute("curvePoints", curvePoints);
            logger.info("Récupération réussie de {} CurvePoints.", curvePoints.size());
            return "curvePoint/list";
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des CurvePoints pour la liste.", e);
            return "error";
        }
    }

    /**
     * Affiche le formulaire d'ajout d'un nouveau CurvePoint.
     * 
     * @param curvePoint Le CurvePoint à ajouter.
     * @return Le nom de la vue pour ajouter un CurvePoint.
     */
    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint curvePoint) {
        logger.info("Affichage du formulaire d'ajout de CurvePoint.");
        return "curvePoint/add";
    }

    /**
     * Valide et enregistre un nouveau CurvePoint.
     * 
     * @param curvePoint Le CurvePoint à valider et enregistrer.
     * @param result Le résultat de la validation.
     * @param model Le modèle pour ajouter les attributs.
     * @return La vue à afficher après la validation.
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        try {
            if (result.hasErrors()) {
                logger.warn("Des erreurs de validation ont été trouvées pour le CurvePoint.");
                model.addAttribute("curvePoint", curvePoint);
                return "curvePoint/add";
            } else {
                curvePointService.addCurvePoint(curvePoint);
                logger.info("CurvePoint ajouté avec succès.");
                return "redirect:/curvePoint/list";
            }
        } catch (Exception e) {
            logger.error("Erreur lors de l'ajout du CurvePoint.", e);
            return "error";
        }
    }

    /**
     * Affiche le formulaire de mise à jour d'un CurvePoint existant.
     * 
     * @param id L'ID du CurvePoint à mettre à jour.
     * @param model Le modèle pour ajouter les attributs.
     * @return Le nom de la vue pour mettre à jour un CurvePoint.
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            logger.info("Récupération du CurvePoint avec l'ID: {}", id);
            CurvePoint curvePoint = curvePointService.getCurvePointById(id);
            model.addAttribute("curvePoint", curvePoint);
            logger.info("CurvePoint avec l'ID {} récupéré avec succès.", id);
            return "curvePoint/update";
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération du CurvePoint avec l'ID: {}", id, e);
            return "error";
        }
    }

    /**
     * Met à jour un CurvePoint existant.
     * 
     * @param id L'ID du CurvePoint à mettre à jour.
     * @param curvePoint Le CurvePoint mis à jour.
     * @param result Le résultat de la validation.
     * @param model Le modèle pour ajouter les attributs.
     * @return La vue à afficher après la mise à jour.
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        try {
            if (result.hasErrors()) {
                logger.warn("Des erreurs de validation ont été trouvées lors de la mise à jour du CurvePoint.");
                model.addAttribute("curvePoint", curvePoint);
                return "curvePoint/update";
            } else {
                curvePointService.updateCurvePoint(id, curvePoint);
                logger.info("CurvePoint avec l'ID {} mis à jour avec succès.", id);
                return "redirect:/curvePoint/list";
            }
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour du CurvePoint avec l'ID: {}", id, e);
            return "error";
        }
    }

    /**
     * Supprime un CurvePoint par son ID.
     * 
     * @param id L'ID du CurvePoint à supprimer.
     * @param model Le modèle pour ajouter les attributs.
     * @return La vue à afficher après la suppression.
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        try {
            logger.info("Suppression du CurvePoint avec l'ID: {}", id);
            curvePointService.deleteCurvePoint(id);
            logger.info("CurvePoint avec l'ID {} supprimé avec succès.", id);
            return "redirect:/curvePoint/list";
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression du CurvePoint avec l'ID: {}", id, e);
            return "error";
        }
    }
}
