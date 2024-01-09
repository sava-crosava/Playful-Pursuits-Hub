package com.sava.playful.pursuits.hub.playfulpursuitshub.service;

import com.sava.playful.pursuits.hub.playfulpursuitshub.model.Category;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    Category findOrCreateCategory(String categoryName);
}
