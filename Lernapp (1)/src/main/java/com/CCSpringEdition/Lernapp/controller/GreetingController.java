package com.CCSpringEdition.Lernapp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreetingController {
    @GetMapping("/greeting")
    public String greeting(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // Holt den Benutzernamen des aktuell authentifizierten Benutzers

        model.addAttribute("authentication" , authentication);
        model.addAttribute("username", username);
        return "greeting/greeting";
    }
}
