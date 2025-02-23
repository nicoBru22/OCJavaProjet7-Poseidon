package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Contrôleur principal de l'application.
 * Gère les pages d'accueil pour les utilisateurs et les administrateurs.
 */
@Controller
public class HomeController {

    private static final Logger logger = LogManager.getLogger(HomeController.class);

    /**
     * Affiche la page d'accueil de l'application.
     * 
     * @param model Le modèle pour ajouter les attributs.
     * @return Le nom de la vue pour la page d'accueil.
     */
    @GetMapping("/")
    public String home(Model model) {
        logger.info("Affichage de la page d'accueil.");
        return "home";
    }

    /**
     * Redirige vers la liste des offres (bidList) pour les administrateurs.
     * 
     * @param model Le modèle pour ajouter les attributs.
     * @return La redirection vers la liste des Bid.
     */
    @GetMapping("/admin/home")
    public String adminHome(Model model) {
        logger.info("Redirection de l'administrateur vers la liste des Bid.");
        return "redirect:/bidList/list";
    }
}

