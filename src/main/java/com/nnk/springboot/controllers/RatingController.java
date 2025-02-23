package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;

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
import jakarta.validation.Valid;

/**
 * Contrôleur pour la gestion des évaluations (ratings).
 * Permet d'ajouter, de mettre à jour, de supprimer et de lister les évaluations.
 */
@Controller
public class RatingController {

    private static final Logger logger = LogManager.getLogger(RatingController.class);

    @Autowired
    private RatingService ratingService;

    /**
     * Affiche la liste de toutes les évaluations.
     * 
     * @param model Le modèle pour transmettre les données à la vue.
     * @return La vue de la liste des évaluations.
     */
    @GetMapping("/rating/list")
    public String home(Model model) {
        logger.info("Affichage de la liste des évaluations.");
        List<Rating> ratings = ratingService.getAllRating();
        model.addAttribute("ratings", ratings);
        return "rating/list";
    }

    /**
     * Affiche le formulaire pour ajouter une nouvelle évaluation.
     * 
     * @param rating L'évaluation à ajouter.
     * @return La vue du formulaire d'ajout d'évaluation.
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        logger.info("Accès au formulaire d'ajout d'une évaluation.");
        return "rating/add";
    }

    /**
     * Valide et ajoute une nouvelle évaluation.
     * 
     * @param rating L'évaluation à ajouter.
     * @param result Le résultat de la validation des données.
     * @param model Le modèle pour transmettre les données à la vue.
     * @return La vue redirigée vers la liste des évaluations.
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Erreur de validation lors de l'ajout d'une évaluation.");
            model.addAttribute("rating", rating);
            return "rating/add";
        }
        logger.info("Évaluation ajoutée avec succès.");
        ratingService.addRating(rating);
        return "redirect:/rating/list";
    }

    /**
     * Affiche le formulaire pour mettre à jour une évaluation existante.
     * 
     * @param id L'identifiant de l'évaluation à mettre à jour.
     * @param model Le modèle pour transmettre les données à la vue.
     * @return La vue du formulaire de mise à jour de l'évaluation.
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("Accès au formulaire de mise à jour de l'évaluation avec l'id : {}", id);
        Rating rating = ratingService.getRatingById(id);
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    /**
     * Valide et met à jour une évaluation existante.
     * 
     * @param id L'identifiant de l'évaluation à mettre à jour.
     * @param rating L'évaluation mise à jour.
     * @param result Le résultat de la validation des données.
     * @param model Le modèle pour transmettre les données à la vue.
     * @return La vue redirigée vers la liste des évaluations.
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Erreur de validation lors de la mise à jour de l'évaluation avec l'id : {}", id);
            model.addAttribute("rating", rating);
            return "rating/update";
        }
        logger.info("Évaluation avec l'id {} mise à jour avec succès.", id);
        ratingService.updateRating(id, rating);
        return "redirect:/rating/list";
    }

    /**
     * Supprime une évaluation.
     * 
     * @param id L'identifiant de l'évaluation à supprimer.
     * @param model Le modèle pour transmettre les données à la vue.
     * @return La vue redirigée vers la liste des évaluations.
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        logger.info("Suppression de l'évaluation avec l'id : {}", id);
        ratingService.deleteRating(id);
        return "redirect:/rating/list";
    }
}

