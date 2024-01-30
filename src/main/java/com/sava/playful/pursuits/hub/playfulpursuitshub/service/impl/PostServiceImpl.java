package com.sava.playful.pursuits.hub.playfulpursuitshub.service.impl;

import com.sava.playful.pursuits.hub.playfulpursuitshub.model.Tag;
import com.sava.playful.pursuits.hub.playfulpursuitshub.model.Post;
import com.sava.playful.pursuits.hub.playfulpursuitshub.repository.TagRepository;
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
    private final TagRepository tagRepository;
    private final StorageService service;

    @Override
    @Transactional
    public Post createPost(Post post, MultipartFile video, MultipartFile image){
        Post postWithFile = savePostWithCategories(post);
        String fullVideoName = service.uploadFile(video);
        String fullImageName = service.uploadFile(image);
        updateFileNames(postWithFile, fullVideoName, fullImageName);
        return postWithFile;
    }

    @Transactional
    @Override
    public String deletePost(Long id) {
        Post post = postRepository.findPostById(id);
        String title = post.getTitle();
        String fileName = post.getVideoName();
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
        String fileName = postRepository.findVideoNameById(postId);
        return service.getPartialObject(fileName, rangeHeader);
    }

    @Override
    public List<Post> findPostsByTagName(String tagName){
        return postRepository.findPostsByTagName(tagName);
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

    @Override
    public Post savePostWithCategories(Post post) {
        Set<Tag> persistedCategories = new HashSet<>();
        for (Tag tag : post.getTags()) {
            Tag persistedTag = tagRepository.findByTagName(tag.getTagName());
            if (persistedTag != null) {
                persistedCategories.add(persistedTag);
            } else {
                persistedCategories.add(tag);
            }
        }
        post.setTags(persistedCategories);

        return postRepository.save(post);
    }
    @Override
    public void updateFileNames(Post post, String videoName, String imageName) {
            post.setVideoName(videoName);
            post.setImageName(imageName);
            postRepository.save(post);
    }
}
