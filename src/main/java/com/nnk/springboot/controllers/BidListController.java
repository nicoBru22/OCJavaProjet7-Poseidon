package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;

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


@Controller
public class BidListController {
    // TODO: Inject Bid service
	
	private Logger logger = LogManager.getLogger(BidListService.class);
	
	@Autowired
	private BidListService bidListService;

    @GetMapping("/bidList/list")
    public String home(Model model) throws Exception {
    	logger.info("Entrée dans la méthode home du bidController /bidList/list");
    	List<BidList> bidLists = bidListService.getAllBid();
    	model.addAttribute("bidLists", bidLists);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
    	logger.info("Tentative d'afficher la page bidList/add.");
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bid list
    	logger.info("Entrée dans la méthode validate du bidController /bidList/add");
    	
        if (result.hasErrors()) {
            logger.warn("Erreurs de validation : {}", result.getAllErrors());
            model.addAttribute("bid", bid);
            return "bidList/add";
        }
        	bidListService.addBid(bid);
        	return "redirect:/bidList/list";
        
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        logger.info("Affichage du formulaire de mise à jour pour l'ID {}", id);
        
        BidList bidList = bidListService.getBidById(id);
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        if (result.hasErrors()) {
            logger.warn("Erreurs de validation : {}", result.getAllErrors());
            model.addAttribute("bid", bidList);
            return "bidList/update";
        }
        
        bidListService.updateBidList(id, bidList);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
    	logger.info("Appel du controller /bidList/delete/{id}");
    	bidListService.deleteBidList(id);
        return "redirect:/bidList/list";
    }
    
}
