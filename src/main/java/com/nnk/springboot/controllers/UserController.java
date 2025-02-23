package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

/**
 * Contrôleur pour la gestion des User.
 * <p>
 * Ce controlleur permet les opérations CRUD des User.
 * </p>
 */
@Controller
public class UserController {
    
    @Autowired
    private UserService userService;

    /**
     * Affiche la liste de tous les utilisateurs.
     * 
     * @param model Le modèle pour transmettre les données à la vue.
     * @return La vue de la liste des utilisateurs.
     */
    @GetMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user/list";
    }

    /**
     * Affiche le formulaire d'ajout d'un nouvel utilisateur.
     * 
     * @param bid L'utilisateur à ajouter.
     * @return La vue du formulaire d'ajout d'un utilisateur.
     */
    @GetMapping("/user/add")
    public String addUser(User user) {
        return "user/add";
    }

    /**
     * Valide les données d'un utilisateur, encode le mot de passe et l'enregistre dans la base de données.
     * 
     * @param user L'utilisateur à valider et enregistrer.
     * @param result Le résultat de la validation des données.
     * @param model Le modèle pour transmettre les données à la vue.
     * @return La vue redirigée vers la liste des utilisateurs si la validation réussit, sinon reste sur le formulaire d'ajout.
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
        	userService.addUser(user);
            return "redirect:/user/list";
        }
        model.addAttribute("user", user);
        return "user/add";
    }

    /**
     * Affiche le formulaire pour mettre à jour un utilisateur existant.
     * 
     * @param id L'identifiant de l'utilisateur à mettre à jour.
     * @param model Le modèle pour transmettre les données à la vue.
     * @return La vue du formulaire de mise à jour de l'utilisateur.
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user/update";
    }

    /**
     * Valide et met à jour un utilisateur existant.
     * 
     * @param id L'identifiant de l'utilisateur à mettre à jour.
     * @param user L'utilisateur mis à jour.
     * @param result Le résultat de la validation des données.
     * @param model Le modèle pour transmettre les données à la vue.
     * @return La vue redirigée vers la liste des utilisateurs si la mise à jour réussit.
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
        	model.addAttribute("user", user);
            return "user/update";
        }
        userService.updateUser(id, user);
        return "redirect:/user/list";
    }

    /**
     * Supprime un utilisateur de la base de données.
     * 
     * @param id L'identifiant de l'utilisateur à supprimer.
     * @param model Le modèle pour transmettre les données à la vue.
     * @return La vue redirigée vers la liste des utilisateurs après suppression.
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
    	userService.deleteUser(id);
        return "redirect:/user/list";
    }
}
