package com.sava.playful.pursuits.hub.playfulpursuitshub.service;

import com.sava.playful.pursuits.hub.playfulpursuitshub.model.MyUser;
import com.sava.playful.pursuits.hub.playfulpursuitshub.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MyUserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public void addUser(MyUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
