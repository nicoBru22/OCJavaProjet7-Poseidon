package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;

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
 * Contrôleur pour la gestion des trades.
 * <p>
 * Ce controlleur permet les opérations CRUD des Trades.
 * </p>
 */
@Controller
public class TradeController {
    
    private static final Logger logger = LogManager.getLogger(TradeController.class);
    
    @Autowired
    private TradeService tradeService;

    /**
     * Affiche la liste des trades.
     * @param model le modèle pour la vue
     * @return la page de liste des trades
     */
    @GetMapping("/trade/list")
    public String home(Model model) {
        logger.info("Affichage de la liste des trades.");
        List<Trade> trades = tradeService.getAllTrade();
        model.addAttribute("trades", trades);
        return "trade/list";
    }

    /**
     * Affiche le formulaire d'ajout d'un trade.
     * @param trade un objet Trade
     * @return la page d'ajout
     */
    @GetMapping("/trade/add")
    public String addUser(Trade trade) {
        logger.info("Affichage du formulaire d'ajout de trade.");
        return "trade/add";
    }

    /**
     * Valide et ajoute un trade.
     * @param trade le trade à ajouter
     * @param result résultat de la validation
     * @param model le modèle pour la vue
     * @return redirection vers la liste des trades
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.warn("Erreur de validation lors de l'ajout du trade.");
            model.addAttribute("trade", trade);
            return "trade/add";
        } else {
            logger.info("Ajout d'un nouveau trade: {}", trade);
            tradeService.addTrade(trade);
            return "redirect:/trade/list";
        }
    }

    /**
     * Affiche le formulaire de mise à jour d'un trade.
     * @param id l'identifiant du trade
     * @param model le modèle pour la vue
     * @return la page de mise à jour
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("Affichage du formulaire de mise à jour pour le trade avec l'ID: {}", id);
        Trade trade = tradeService.getTradeById(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    /**
     * Met à jour un trade.
     * @param id l'identifiant du trade
     * @param trade les nouvelles valeurs du trade
     * @param result résultat de la validation
     * @param model le modèle pour la vue
     * @return redirection vers la liste des trades
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.warn("Erreur de validation lors de la mise à jour du trade avec l'ID: {}", id);
            model.addAttribute("trade", trade);
            return "trade/update";
        } else {
            logger.info("Mise à jour du trade avec l'ID: {}", id);
            tradeService.updateTrade(id, trade);
            return "redirect:/trade/list";
        }
    }

    /**
     * Supprime un trade.
     * @param id l'identifiant du trade
     * @param model le modèle pour la vue
     * @return redirection vers la liste des trades
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        logger.info("Suppression du trade avec l'ID: {}", id);
        tradeService.deleteTrade(id);
        return "redirect:/trade/list";
    }
}