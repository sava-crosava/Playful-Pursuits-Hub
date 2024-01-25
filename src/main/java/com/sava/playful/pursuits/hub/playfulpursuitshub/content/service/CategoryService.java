package com.sava.playful.pursuits.hub.playfulpursuitshub.content.service;

import com.sava.playful.pursuits.hub.playfulpursuitshub.content.model.Category;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    Category findOrCreateCategory(String categoryName);
}
