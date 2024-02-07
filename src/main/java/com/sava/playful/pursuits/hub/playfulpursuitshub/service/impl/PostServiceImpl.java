package com.sava.playful.pursuits.hub.playfulpursuitshub.service.impl;

import com.sava.playful.pursuits.hub.playfulpursuitshub.DTO.PostResponseDTO;
import com.sava.playful.pursuits.hub.playfulpursuitshub.model.Channel;
import com.sava.playful.pursuits.hub.playfulpursuitshub.model.Post;
import com.sava.playful.pursuits.hub.playfulpursuitshub.model.Tag;
import com.sava.playful.pursuits.hub.playfulpursuitshub.repository.PostRepository;
import com.sava.playful.pursuits.hub.playfulpursuitshub.repository.TagRepository;
import com.sava.playful.pursuits.hub.playfulpursuitshub.service.PostService;
import com.sava.playful.pursuits.hub.playfulpursuitshub.service.StorageService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Primary
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final StorageService storageService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Post createPost(Post post, MultipartFile video, MultipartFile thumbnail){
        String fullVideoName = storageService.uploadVideo(video);
        String fullThumbnailName = storageService.uploadThumbnail(thumbnail);
        Post postWithFile = savePostWithCategories(post);
        updateFileNames(postWithFile, fullVideoName, fullThumbnailName);
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
            storageService.deleteFile(fileName);
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
        return storageService.getPartialObject(fileName, rangeHeader);
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
            post.setThumbnailsImageName(imageName);
            postRepository.save(post);
    }
    @Override
    public Page<PostResponseDTO> findPostsWithChannelInfo(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);

        return posts.map(post -> {
            PostResponseDTO dto = modelMapper.map(post, PostResponseDTO.class);
            dto.setThumbnailImageUrl(storageService.getThumbnailImageUrl(post.getThumbnailsImageName()));

            Channel channel = post.getChannel();
            dto.setChannelName(channel.getChannelName());
            dto.setChannelIconImageUrl(storageService.getChannelIconImageUrl(channel.getIconImageName()));

            return dto;
        });
    }
}
