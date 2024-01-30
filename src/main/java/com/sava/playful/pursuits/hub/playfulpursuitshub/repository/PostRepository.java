package com.sava.playful.pursuits.hub.playfulpursuitshub.repository;

import com.sava.playful.pursuits.hub.playfulpursuitshub.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findPostByTitle(String title);
    Post findPostById(Long id);
    @Query("SELECT p FROM Post p JOIN FETCH p.tags c WHERE c.tagName = :tagName")
    List<Post> findPostsByTagName(@Param("tagName") String tagName);

    @Query("SELECT p.videoName FROM Post p WHERE p.id = :postId")
    String findVideoNameById(Long postId);
}

