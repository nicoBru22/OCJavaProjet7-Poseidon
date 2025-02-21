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

@Controller
public class CurveController {
    // TODO: Inject Curve Point service
	
	Logger logger = LogManager.getLogger(CurveController.class);
	
	@Autowired
	private CurvePointService curvePointService;

    @GetMapping("/curvePoint/list")
    public String home(Model model)
    {
        // TODO: find all Curve Point, add to model
    	List<CurvePoint> curvePoints = curvePointService.getAllCurvePoint();
    	model.addAttribute("curvePoints", curvePoints);
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Curve list
    	if (result.hasErrors()) {
        	model.addAttribute("curvePoint", curvePoint);
            return "curvePoint/add";

    	} else {
    		curvePointService.addCurvePoint(curvePoint);
    		return "redirect:/curvePoint/list";
    	}


    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form
    	CurvePoint curvePoint = curvePointService.getCurvePointById(id);
    	model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Curve and return Curve list
    	if (result.hasErrors()) {
    		model.addAttribute("curvePoint", curvePoint);
    		return "curvePoint/update";
    	} else {
    		curvePointService.updateCurvePoint(id, curvePoint);
            return "redirect:/curvePoint/list";
    	}

    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
    	curvePointService.deleteCurvePoint(id);
        return "redirect:/curvePoint/list";
    }
}
