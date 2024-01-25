package com.sava.playful.pursuits.hub.playfulpursuitshub.content.repository;

import com.sava.playful.pursuits.hub.playfulpursuitshub.content.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByNameCategory(String categoryName);
}
