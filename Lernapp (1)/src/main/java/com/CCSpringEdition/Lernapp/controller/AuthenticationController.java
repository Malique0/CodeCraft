package com.CCSpringEdition.Lernapp.controller;


import com.CCSpringEdition.Lernapp.entity.AppUser;
import com.CCSpringEdition.Lernapp.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.ReaderEditor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    /**LOGIN-ANMELDUNG**/
    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("pageTitel", "Anmeldung_CodeCraft");
        return "authentication/login";
    }

    @PostMapping("/login")
    public String Anmeldung(@RequestParam String usernameOrEmail, @RequestParam String password, Model model) {
        // Suche nach Benutzer mit Benutzername oder E-Mail
        Optional<AppUser> optionalUser = appUserRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

        // Überprüfe, ob der Benutzer existiert und das Passwort übereinstimmt
        if (optionalUser.isPresent() && passwordEncoder.matches(password, optionalUser.get().getPassword())) {
            model.addAttribute("username", usernameOrEmail);// Benutzername für die nächste Seite
            return "redirect:authentication/greeting?username=" + usernameOrEmail; // Weiterleitung zur Begrüßungsseite
        } else {
            model.addAttribute("error", "Ungültiger Email/Benutzername oder Passwort");
            return "authentication/login"; // Zeige die Login-Seite erneut mit Fehlermeldung
        }
    }


    /**REGISTER-REGISTRIERUNG**/
    @GetMapping("/register")
    public String showRegister(Model model)
    {
        model.addAttribute("pageTitel", "Registrierung");
        return "authentication/register";
    }

    @PostMapping("/register")
    public String Registrieren(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam String firstname,
            @RequestParam String lastname,
            RedirectAttributes redirectAttributes,Model model) {

        // Loggen der Eingabedaten für Debugging-Zwecke
        log.debug("Registrierungsversuch: Benutzername={}, E-Mail={}", username, email);

        // Überprüfen, ob Benutzername oder E-Mail bereits registriert sind
        if (appUserRepository.findByUsernameOrEmail(username, email).isPresent()) {
            log.warn("Benutzername oder E-Mail bereits registriert: Benutzername={}, E-Mail={}", username, email);
            model.addAttribute("error", "Benutzername oder E-Mail bereits registriert ");
            redirectAttributes.addFlashAttribute("error","Benutzername oder E-Mail bereits registriert");
            return "redirect:/authentication/register";
        }

        // Überprüfen, ob die Passwörter übereinstimmen
        if (!password.equals(confirmPassword)) {
            log.warn("Passwörter stimmen nicht überein: Passwort={}, Bestätigung={}", password, confirmPassword);
            model.addAttribute("error", "Passwörter stimmen nicht überein");
            redirectAttributes.addFlashAttribute("error", "Passwörter stimmen nicht überein");
            return "redirect:/authentication/register";
        }

        // Benutzerobjekt erstellen
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setEmail(email);
        appUser.setFirstname(firstname);
        appUser.setLastname(lastname);
        /*List<String> roles = new ArrayList<String>();
        roles.add(appUserRepository.count() == 0 ? "ROLE_ADMIN" : "ROLE_USER");
        appUser.setRoles(roles);*/

        // Passwort verschlüsseln
        String encodedPassword = passwordEncoder.encode(password);
        log.debug("Passwort erfolgreich verschlüsselt.");
        appUser.setPassword(encodedPassword);

        // Benutzer speichern
        try {
            appUserRepository.save(appUser);
            log.info("Benutzer erfolgreich registriert: Benutzername={}, E-Mail={}", username, email);
        } catch (Exception e) {
            log.error("Fehler beim Speichern des Benutzers: ", e);
            model.addAttribute("error", "Ein Fehler ist aufgetreten. Bitte versuche es erneut.");
            return "authentication/register";
        }


        // Erfolgsnachricht

        redirectAttributes.addFlashAttribute("message", "Registrierung erfolgreich!");
        model.addAttribute("message", "Registrierung erfolgreich!");
        return "redirect:/home";
    }


}
