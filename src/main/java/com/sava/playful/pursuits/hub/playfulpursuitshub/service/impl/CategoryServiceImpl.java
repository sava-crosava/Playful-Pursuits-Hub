package com.sava.playful.pursuits.hub.playfulpursuitshub.service.impl;

import com.sava.playful.pursuits.hub.playfulpursuitshub.model.Category;
import com.sava.playful.pursuits.hub.playfulpursuitshub.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CategoryServiceImpl {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    //todo delete this and his interface
    public Category findOrCreateCategory(String categoryName) {
        Category existingCategory = categoryRepository.findByNameCategory(categoryName);

        if (existingCategory != null) {
            // Категорія вже існує, повертаємо її
            return existingCategory;
        } else {
            // Категорія не існує, створюємо нову
            Category newCategory = new Category();
            newCategory.setNameCategory(categoryName);
            categoryRepository.save(newCategory);
            return newCategory;
        }
    }
}
