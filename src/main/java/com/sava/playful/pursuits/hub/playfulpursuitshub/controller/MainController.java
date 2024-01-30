package com.sava.playful.pursuits.hub.playfulpursuitshub.controller;


import com.sava.playful.pursuits.hub.playfulpursuitshub.model.Tag;
import com.sava.playful.pursuits.hub.playfulpursuitshub.model.Post;
import com.sava.playful.pursuits.hub.playfulpursuitshub.service.PostService;
import com.sava.playful.pursuits.hub.playfulpursuitshub.service.StorageService;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.core.io.ByteArrayResource;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;


import java.util.List;

@RestController
@RequestMapping("api/v1/pphub")
@AllArgsConstructor
public class MainController
{
    private final PostService postService;

    private final StorageService storageService;

    //TODO browsing error (ignore or log)
    @GetMapping(value = "findVideoById/{postId}", produces = { MediaType.APPLICATION_OCTET_STREAM_VALUE })
    public ResponseEntity<StreamingResponseBody> getPartialObject(
            @PathVariable Long postId,
            @RequestHeader(value = "Range", required = false) String rangeHeader) {

        return postService.getPartialObject(postId, rangeHeader);
    }

    @GetMapping("home")
    public List<Post> findAllPost(){
        return postService.findAllPost();
    }

    @GetMapping("findByTag/{tagName}")
    public List<Post> findPostsByTagName(@PathVariable String tagName){
        return postService.findPostsByTagName(tagName);
    }

    //TODO when creating a query, is it better to return a message instead of a post?
    @PostMapping("createNewPost")
    public ResponseEntity<?> createPost( @RequestParam(value = "video") MultipartFile video,
                                         @RequestParam(value = "image") MultipartFile image,
                                         @RequestPart("post") Post post) {
        if (post == null) {
            return ResponseEntity.badRequest().body("Post cannot be null");
        }
        if (video == null || video.isEmpty()) {
            return ResponseEntity.badRequest().body("File cannot be null or empty");
        }
        for (Tag tag : post.getTags()) {
            if (tag == null) {
                return ResponseEntity.badRequest().body("Tag cannot be null");
            }
        }
        try {
            return ResponseEntity.ok(postService.createPost(post, video, image));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request");
        }
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePostById(@PathVariable Long postId) {
        try {
            return new ResponseEntity<>(postService.deletePost(postId), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request");
        }
    }

    @GetMapping("/download/{postId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long postId) {
        String fileName = postService.findPostById(postId).getVideoName();
        byte[] data = storageService.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

}
