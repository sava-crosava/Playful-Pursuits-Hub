package com.sava.playful.pursuits.hub.playfulpursuitshub.repository;

import com.sava.playful.pursuits.hub.playfulpursuitshub.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByTagName(String tagName);
}
