package experiment212.controller;

import experiment212.model.ApiResponse;
import experiment212.model.Post;
import experiment212.service.PostService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:5173")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<ApiResponse<Post>> createPost(
            @Valid @RequestBody Post post) {

        Post createdPost = postService.createPost(post);

        return new ResponseEntity<>(
                new ApiResponse<>(true, "Post created successfully", createdPost),
                HttpStatus.CREATED
        );
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<ApiResponse<List<Post>>> getAllPosts() {

        List<Post> posts = postService.getAllPosts();

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Posts retrieved successfully", posts)
        );
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Post>> getPostById(
            @PathVariable Long id) {

        Post post = postService.getPostById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Post retrieved successfully", post)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Post>> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody Post post) {

        Post updatedPost = postService.updatePost(id, post);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Post updated successfully", updatedPost)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletePost(
            @PathVariable Long id) {

        postService.deletePost(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Post deleted successfully", null)
        );
    }
}