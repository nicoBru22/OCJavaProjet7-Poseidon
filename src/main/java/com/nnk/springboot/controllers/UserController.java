package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;

import Exception.UserExistingException;

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


    @GetMapping("/user/add")
    public String addUser(User user) {
        return "user/add";
    }


    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "user/add";
        }

        try {
            userService.addUser(user);
            return "redirect:/user/list";
        } catch (UserExistingException e) {
            model.addAttribute("user", user);
            model.addAttribute("errorMessage", e.getMessage());
            return "user/add";
        }
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
        try {
            userService.updateUser(id, user);
            return "redirect:/user/list";
        } catch (UserExistingException e) {
        	model.addAttribute("user", user);
        	model.addAttribute("errorMessage", e.getMessage());
        	return "user/update";
        }

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
