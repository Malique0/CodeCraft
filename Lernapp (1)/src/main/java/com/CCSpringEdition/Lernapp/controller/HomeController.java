package com.CCSpringEdition.Lernapp.controller;


import com.CCSpringEdition.Lernapp.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class HomeController {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/home")
    public String home(Model model)
    {
        model.addAttribute("pageTitel", "CodeCraft");
        return "home";
    }
    @GetMapping("/")
    public String startseite(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("pageTitel", "CodeCraft");
        model.addAttribute("authentication" , authentication);
        return "home";
    }

    /*//Anfrage an die Root-Url("/")
    @GetMapping("/checkUser")
    public String ckeckUser() {
        //Überprüfen ob es bereits ein Benutzer in der Datenbank gibt
        if(appUserRepository.count() == 0)
        {
            return  "redirect:/authentication/register";
        }
        return "authentication/home";
    }*/

}
