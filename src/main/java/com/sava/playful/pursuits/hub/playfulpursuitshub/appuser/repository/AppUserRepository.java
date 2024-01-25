package com.sava.playful.pursuits.hub.playfulpursuitshub.appuser.repository;

import com.sava.playful.pursuits.hub.playfulpursuitshub.appuser.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUserName(String userName);
}
