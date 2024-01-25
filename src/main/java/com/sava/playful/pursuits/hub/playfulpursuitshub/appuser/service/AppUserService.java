package com.sava.playful.pursuits.hub.playfulpursuitshub.appuser.service;

import com.sava.playful.pursuits.hub.playfulpursuitshub.appuser.model.AppUser;
import com.sava.playful.pursuits.hub.playfulpursuitshub.appuser.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService {
    private AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;

    public void addUser(AppUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        appUserRepository.save(user);
    }
}
