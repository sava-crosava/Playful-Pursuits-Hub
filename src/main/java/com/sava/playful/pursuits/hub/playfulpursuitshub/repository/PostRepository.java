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
    @Query("SELECT p FROM Post p JOIN FETCH p.categories c WHERE c.nameCategory = :categoryName")
    List<Post> findPostsByCategoryName(@Param("categoryName") String categoryName);

    @Query("SELECT p.fileName FROM Post p WHERE p.id = :postId")
    String findFileNameById(Long postId);
}

