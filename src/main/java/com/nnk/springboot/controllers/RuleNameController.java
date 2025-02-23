package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Contrôleur pour la gestion des noms de règles (RuleName).
 * Permet d'ajouter, de mettre à jour, de supprimer et de lister les noms de règles.
 */
@Controller
public class RuleNameController {

    private static final Logger logger = LogManager.getLogger(RuleNameController.class);

    @Autowired
    private RuleNameService ruleNameService;

    /**
     * Affiche la liste de tous les noms de règles.
     * 
     * @param model Le modèle pour transmettre les données à la vue.
     * @return La vue de la liste des noms de règles.
     */
    @GetMapping("/ruleName/list")
    public String home(Model model) {
        logger.info("Affichage de la liste des noms de règles.");
        List<RuleName> ruleNames = ruleNameService.getAllRuleName();
        model.addAttribute("ruleNames", ruleNames);
        return "ruleName/list";
    }

    /**
     * Affiche le formulaire pour ajouter un nouveau nom de règle.
     * 
     * @param ruleName Le nom de règle à ajouter.
     * @return La vue du formulaire d'ajout d'un nom de règle.
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
        logger.info("Accès au formulaire d'ajout d'un nom de règle.");
        return "ruleName/add";
    }

    /**
     * Valide et ajoute un nouveau nom de règle.
     * 
     * @param ruleName Le nom de règle à ajouter.
     * @param result Le résultat de la validation des données.
     * @param model Le modèle pour transmettre les données à la vue.
     * @return La vue redirigée vers la liste des noms de règles.
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Erreur de validation lors de l'ajout du nom de règle.");
            model.addAttribute("ruleName", ruleName);
            return "ruleName/add";
        }
        logger.info("Nom de règle ajouté avec succès.");
        ruleNameService.addRuleName(ruleName);
        return "redirect:/ruleName/list";
    }

    /**
     * Affiche le formulaire pour mettre à jour un nom de règle existant.
     * 
     * @param id L'identifiant du nom de règle à mettre à jour.
     * @param model Le modèle pour transmettre les données à la vue.
     * @return La vue du formulaire de mise à jour du nom de règle.
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("Accès au formulaire de mise à jour du nom de règle avec l'id : {}", id);
        RuleName ruleName = ruleNameService.getRuleNameById(id);
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    /**
     * Valide et met à jour un nom de règle existant.
     * 
     * @param id L'identifiant du nom de règle à mettre à jour.
     * @param ruleName Le nom de règle mis à jour.
     * @param result Le résultat de la validation des données.
     * @param model Le modèle pour transmettre les données à la vue.
     * @return La vue redirigée vers la liste des noms de règles.
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Erreur de validation lors de la mise à jour du nom de règle avec l'id : {}", id);
            model.addAttribute("ruleName", ruleName);
            return "ruleName/update";
        }
        logger.info("Nom de règle avec l'id {} mis à jour avec succès.", id);
        ruleNameService.updateRuleName(id, ruleName);
        return "redirect:/ruleName/list";
    }

    /**
     * Supprime un nom de règle.
     * 
     * @param id L'identifiant du nom de règle à supprimer.
     * @param model Le modèle pour transmettre les données à la vue.
     * @return La vue redirigée vers la liste des noms de règles.
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        logger.info("Suppression du nom de règle avec l'id : {}", id);
        ruleNameService.deleteRuleName(id);
        return "redirect:/ruleName/list";
    }
}

