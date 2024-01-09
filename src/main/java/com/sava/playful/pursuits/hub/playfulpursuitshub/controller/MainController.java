package com.sava.playful.pursuits.hub.playfulpursuitshub.controller;


import com.sava.playful.pursuits.hub.playfulpursuitshub.model.Category;
import com.sava.playful.pursuits.hub.playfulpursuitshub.model.Post;
import com.sava.playful.pursuits.hub.playfulpursuitshub.service.PostService;
import com.sava.playful.pursuits.hub.playfulpursuitshub.service.StorageService;
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
@RequestMapping
@AllArgsConstructor
public class MainController
{
    private final PostService postService;

    private final StorageService storageService;


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

    @GetMapping("findByCategory/{categoryName}")
    public List<Post> findPostsByCategoryName(@PathVariable String categoryName){
        return postService.findPostsByCategoryName(categoryName);
    }


    @PostMapping("createNewPost")
    public ResponseEntity<?> createPost( @RequestParam(value = "file") MultipartFile file,
                                         @RequestPart("post") Post post) {
        if (post == null) {
            return ResponseEntity.badRequest().body("Post cannot be null");
        }
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("File cannot be null or empty");
        }
        for (Category category : post.getCategories()) {
            if (category == null) {
                return ResponseEntity.badRequest().body("Category cannot be null");
            }
        }
        try {
            return ResponseEntity.ok(postService.createPost(post, file));
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
        String fileName = postService.findPostById(postId).getFileName();
        byte[] data = storageService.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }


//    @GetMapping()
//    public String getHello(){
//        //todo
//        return "Hello";
//    }
}
