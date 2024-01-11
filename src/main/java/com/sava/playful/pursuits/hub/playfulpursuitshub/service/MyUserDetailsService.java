package com.sava.playful.pursuits.hub.playfulpursuitshub.service;

import com.sava.playful.pursuits.hub.playfulpursuitshub.config.MyUserDetails;
import com.sava.playful.pursuits.hub.playfulpursuitshub.model.MyUser;
import com.sava.playful.pursuits.hub.playfulpursuitshub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> user = userRepository.findByUserName(username);
        return user.map(MyUserDetails::new)
                .orElseThrow((() -> new UsernameNotFoundException(username + "not found")));
    }
}
