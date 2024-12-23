package com.CCSpringEdition.Lernapp.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Autorisieren von HTTP-Anfragen
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/home", "/login", "/register", "/css/**","/js/**").permitAll()
                        //.requestMatchers("/admin/**").hasRole("ROLE_ADMIN")
                        //.requestMatchers("/user/**").hasAnyRole("ROLE_USER","ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                // Konfiguration der Formular-basierten Anmeldung
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("usernameOrEmail")  // Benutzerdefinierter Parameter
                        .passwordParameter("password")        // Standardfeld für Passwort
                        .defaultSuccessUrl("/greeting", true)
                        .permitAll()
                )
                // Konfiguration des Logout-Verhaltens
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                );

        return http.build();
    }
}
