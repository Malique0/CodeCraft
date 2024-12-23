package com.CCSpringEdition.Lernapp.service;


import com.CCSpringEdition.Lernapp.entity.AppUser;
import com.CCSpringEdition.Lernapp.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomAppUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        //Benutzer anhand von Benutzernamen oder Email finden
        AppUser appUser = appUserRepository.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Benutzer nicht gefunden mit: " + usernameOrEmail));

                //Userdeatails zurückgeben
        return org.springframework.security.core.userdetails.User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())
                .authorities("USER") // Temporär: Ohne Rollen


                .build();
    }


}
