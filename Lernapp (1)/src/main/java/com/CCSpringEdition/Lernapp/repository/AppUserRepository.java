package com.CCSpringEdition.Lernapp.repository;

import com.CCSpringEdition.Lernapp.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    //Suche eines Benutzers anhand von Benutzernamen oder Email
    Optional<AppUser> findByUsernameOrEmail(String username, String email);



}
