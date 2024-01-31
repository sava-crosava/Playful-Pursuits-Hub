package com.sava.playful.pursuits.hub.playfulpursuitshub.repository;

import com.sava.playful.pursuits.hub.playfulpursuitshub.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByUserName(String userName);
}
