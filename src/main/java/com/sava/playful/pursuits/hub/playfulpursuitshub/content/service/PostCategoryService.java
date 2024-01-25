package com.sava.playful.pursuits.hub.playfulpursuitshub.content.service;

import com.sava.playful.pursuits.hub.playfulpursuitshub.content.model.Category;
import com.sava.playful.pursuits.hub.playfulpursuitshub.content.model.Post;
import com.sava.playful.pursuits.hub.playfulpursuitshub.content.repository.CategoryRepository;
import com.sava.playful.pursuits.hub.playfulpursuitshub.content.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostCategoryService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public PostCategoryService(PostRepository postRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }



    public void addCategoryToPost(Long postId, Long categoryId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));

        post.getCategories().add(category);
        category.getPosts().add(post);

        postRepository.save(post);
        categoryRepository.save(category);
    }

    public List<Post> findAllPost() {
        return postRepository.findAll();
    }
}

