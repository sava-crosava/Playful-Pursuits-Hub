package com.sava.playful.pursuits.hub.playfulpursuitshub.repository;

import com.sava.playful.pursuits.hub.playfulpursuitshub.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByNameCategory(String categoryName);
}
