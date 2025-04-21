package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {
    
    @GetMapping("/403")
    public String accesDenied(HttpServletRequest request, Model model) {
        String username = request.getRemoteUser();
        
        model.addAttribute("username", username);
        
        return "403";
    }
}