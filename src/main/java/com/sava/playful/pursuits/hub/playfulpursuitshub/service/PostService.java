package com.sava.playful.pursuits.hub.playfulpursuitshub.service;

import com.sava.playful.pursuits.hub.playfulpursuitshub.DTO.PostResponseDTO;
import com.sava.playful.pursuits.hub.playfulpursuitshub.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;

public interface PostService {
    List<Post> findAllPost();
    Post findByTitle(String title);
    Post updatePost(Post post);

    List<Post> findPostsByTagName(String tagName);

    Post savePostWithCategories(Post post);

    Post createPost(Post post, MultipartFile video, MultipartFile image);

    String deletePost(Long postId);

    Post findPostById(Long postId);

    ResponseEntity<StreamingResponseBody> getPartialObject(Long postId, String rangeHeader);
    void updateFileNames(Post post, String videoName, String imageName);

    public Page<PostResponseDTO> findPostsWithChannelInfo(Pageable pageable);
}
