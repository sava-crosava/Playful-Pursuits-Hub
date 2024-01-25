package com.sava.playful.pursuits.hub.playfulpursuitshub.content.service;

import com.sava.playful.pursuits.hub.playfulpursuitshub.content.model.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;

public interface PostService {
    List<Post> findAllPost();
    Post findByTitle(String title);
    Post updatePost(Post post);

    List<Post> findPostsByCategoryName(String categoryName);

    Post savePostWithCategories(Post post);

    Post createPost(Post post, MultipartFile file);

    String deletePost(Long postId);

    Post findPostById(Long postId);

    ResponseEntity<StreamingResponseBody> getPartialObject(Long postId, String rangeHeader);
}
