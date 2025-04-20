package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Contrôleur de la gestion de l'authentification et de l'accès sécurisé.
 * Gère les pages de connexion, la liste des utilisateurs et la gestion des erreurs d'accès.
 */
@Controller
@RequestMapping("app")
public class LoginController {

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Affiche la page de connexion.
     * 
     * @return La vue de la page de connexion.
     */
    @GetMapping("/login")
    public ModelAndView login() {
        logger.info("Accès à la page de connexion.");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    /**
     * Affiche la liste de tous les utilisateurs.
     * 
     * @return La vue contenant la liste des utilisateurs.
     */
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        logger.info("Affichage de la liste des utilisateurs.");
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    /**
     * Gère l'erreur d'accès lorsque l'utilisateur n'est pas autorisé à accéder aux données demandées.
     * 
     * @return La vue d'erreur avec le message approprié.
     */
    @GetMapping("error")
    public ModelAndView error() {
        logger.error("Tentative d'accès non autorisé.");
        ModelAndView mav = new ModelAndView();
        String errorMessage = "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }
}

