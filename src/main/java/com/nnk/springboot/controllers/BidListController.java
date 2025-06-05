package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.IBidListService;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;


/**
 * Contrôleur pour la gestion des BidList.
 */
@Controller
public class BidListController {

    private static final Logger logger = LogManager.getLogger(BidListController.class);

    @Autowired
    private IBidListService bidListService;

    /**
     * Affiche la liste des BidList.
     *
     * @param model Modèle pour la vue
     * @return La page bidList/list
     * @throws Exception si une erreur survient
     */
    @GetMapping("/bidList/list")
    public String home(Model model) throws Exception {
        logger.info("Entrée dans la méthode home - Récupération de la liste des BidList");
            List<BidList> bidLists = bidListService.getAllBid();
            model.addAttribute("bidLists", bidLists);
            return "bidList/list";
    }

    /**
     * Affiche le formulaire pour ajouter un nouveau BidList.
     *
     * @param bid Objet BidList vide pour le formulaire
     * @return La page bidList/add
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        logger.info("Affichage du formulaire d'ajout d'un BidList");
        return "bidList/add";
    }

    /**
     * Valide et ajoute un nouveau BidList.
     *
     * @param bid    Objet BidList à ajouter
     * @param result Résultat de la validation
     * @param model  Modèle pour la vue
     * @return Redirection vers la liste des BidList si succès, sinon retour au formulaire d'ajout
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, RedirectAttributes redirectAttributes) {
        logger.info("Tentative d'ajout d'un nouveau BidList");

        if (result.hasErrors()) {
            logger.warn("Erreurs de validation : {}", result.getAllErrors());
            return "bidList/add";
        }

        BidList savedBid = bidListService.addBid(bid);
        redirectAttributes.addFlashAttribute("info", "Ce Bid a bien été ajouté avec succès, ID : " + savedBid.getBidListId());

        return "redirect:/bidList/list";
    }



    /**
     * Affiche le formulaire de mise à jour d'un BidList.
     *
     * @param id    ID du BidList à mettre à jour
     * @param model Modèle pour la vue
     * @return La page bidList/update
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("Affichage du formulaire de mise à jour pour le BidList ID {}", id);
        
        BidList bidList = bidListService.getBidById(id);
        if (bidList == null) {
            logger.warn("Aucun BidList trouvé avec l'ID {}", id);
            return "redirect:/bidList/list";
        }
        
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    /**
     * Met à jour un BidList existant.
     *
     * @param id       ID du BidList à mettre à jour
     * @param bidList  Objet BidList mis à jour
     * @param result   Résultat de la validation
     * @param model    Modèle pour la vue
     * @return Redirection vers la liste des BidList si succès, sinon retour au formulaire de mise à jour
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        logger.info("Mise à jour du BidList ID {}", id);

        if (result.hasErrors()) {
            logger.warn("Erreurs de validation lors de la mise à jour du BidList ID {} : {}", id, result.getAllErrors());
            model.addAttribute("bid", bidList);
            return "bidList/update";
        }

        bidListService.updateBidList(id, bidList);
        logger.info("BidList ID {} mis à jour avec succès", id);
        return "redirect:/bidList/list";
    }

    /**
     * Supprime un BidList.
     *
     * @param id ID du BidList à supprimer
     * @param model Modèle pour la vue
     * @return Redirection vers la liste des BidList après suppression
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        logger.info("Suppression du BidList ID {}", id);

        bidListService.deleteBidList(id);
        logger.info("BidList ID {} supprimé avec succès", id);
        return "redirect:/bidList/list";
    }
}
