package com.sava.playful.pursuits.hub.playfulpursuitshub.service;

import com.sava.playful.pursuits.hub.playfulpursuitshub.model.Tag;
import com.sava.playful.pursuits.hub.playfulpursuitshub.model.Post;
import com.sava.playful.pursuits.hub.playfulpursuitshub.repository.TagRepository;
import com.sava.playful.pursuits.hub.playfulpursuitshub.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostTagService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    @Autowired
    public PostTagService(PostRepository postRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }



    public void addTagToPost(Long postId, Long tagId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new RuntimeException("Tag not found"));

        post.getTags().add(tag);
        tag.getPosts().add(post);

        postRepository.save(post);
        tagRepository.save(tag);
    }

    public List<Post> findAllPost() {
        return postRepository.findAll();
    }
}

