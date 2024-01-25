package com.sava.playful.pursuits.hub.playfulpursuitshub.appuser.service;


import com.sava.playful.pursuits.hub.playfulpursuitshub.appuser.model.AppUser;
import com.sava.playful.pursuits.hub.playfulpursuitshub.appuser.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class AppUserDetailsService implements UserDetailsService {
    private static final String USER_NOT_FOUND_MSG = "user with username %s not found";

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        return appUserRepository.findByUserName(userName)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, userName)));
    }

    public String singUpUser(AppUser appUser){
        boolean userExists = appUserRepository.findByUserName(appUser.getUsername())
                .isPresent();

        if (userExists){
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = passwordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        // TODO: send confirmation token

        return "it works";
    }
}
