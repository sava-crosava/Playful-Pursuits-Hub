package com.sava.playful.pursuits.hub.playfulpursuitshub.service.impl;

import com.sava.playful.pursuits.hub.playfulpursuitshub.model.Category;
import com.sava.playful.pursuits.hub.playfulpursuitshub.model.Post;
import com.sava.playful.pursuits.hub.playfulpursuitshub.repository.CategoryRepository;
import com.sava.playful.pursuits.hub.playfulpursuitshub.repository.PostRepository;
import com.sava.playful.pursuits.hub.playfulpursuitshub.service.PostService;
import com.sava.playful.pursuits.hub.playfulpursuitshub.service.StorageService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Primary
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final StorageService service;

    @Override
    @Transactional
    public Post createPost(Post post, MultipartFile file){
        Post postWithFile = savePostWithCategories(post);
        String fullFileName = service.uploadFile(file);
        updateNameFile(postWithFile.getId(), fullFileName);
        return postWithFile;
    }

    @Transactional
    @Override
    public String deletePost(Long id) {
        Post post = postRepository.findPostById(id);
        String title = post.getTitle();
        String fileName = post.getFileName();
        if(post != null){
            postRepository.deleteById(id);
            service.deleteFile(fileName);
        }
        System.out.println(title + " removed ...");
        return title + " removed ...";
    }

    @Override
    public Post findPostById(Long postId) {
        return postRepository.findPostById(postId);
    }

    @Override
    public ResponseEntity<StreamingResponseBody> getPartialObject(Long postId, String rangeHeader) {
        String fileName = postRepository.findFileNameById(postId);
        return service.getPartialObject(fileName, rangeHeader);
    }

    public List<Post> findPostsByCategoryName(String categoryName){
        return postRepository.findPostsByCategoryName(categoryName);
    }

    @Override
    public List<Post> findAllPost() {
        return postRepository.findAll();
    }

    @Override
    public Post findByTitle(String title) {
        return postRepository.findPostByTitle(title);
    }

    @Override
    public Post updatePost(Post post) {
        return null;
    }


    public Post savePostWithCategories(Post post) {
        Set<Category> persistedCategories = new HashSet<>();
        for (Category category : post.getCategories()) {
            Category persistedCategory = categoryRepository.findByNameCategory(category.getNameCategory());
            if (persistedCategory != null) {
                persistedCategories.add(persistedCategory);
            } else {
                persistedCategories.add(category);
            }
        }
        post.setCategories(persistedCategories);

        return postRepository.save(post);
    }

    public Post updateNameFile(Long postId, String newNameFile) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setFileName(newNameFile);
            return postRepository.save(post);
        } else {
            throw new EntityNotFoundException("Post with id " + postId + " not found");
        }
    }
}
