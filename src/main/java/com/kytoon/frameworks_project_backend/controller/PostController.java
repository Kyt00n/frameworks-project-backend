package com.kytoon.frameworks_project_backend.controller;

import com.kytoon.frameworks_project_backend.dto.ImageDto;
import com.kytoon.frameworks_project_backend.model.Comment;
import com.kytoon.frameworks_project_backend.model.Image;
import com.kytoon.frameworks_project_backend.model.Post;
import com.kytoon.frameworks_project_backend.request.AddCommentRequest;
import com.kytoon.frameworks_project_backend.request.AddPostRequest;
import com.kytoon.frameworks_project_backend.response.ApiResponse;
import com.kytoon.frameworks_project_backend.response.GetPostResponse;
import com.kytoon.frameworks_project_backend.service.comment.ICommentService;
import com.kytoon.frameworks_project_backend.service.image.IImageService;
import com.kytoon.frameworks_project_backend.service.post.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final IPostService postService;
    private final ICommentService commentService;
    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadPost(@RequestBody AddPostRequest postRequest) {
        try {
            Post createdPost = postService.createPost(postRequest);
            return ResponseEntity.ok(new ApiResponse("Post created successfully", createdPost));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error creating post", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllPosts(@RequestParam(value = "limit", required = false) Integer limit) {
        try {
            List<GetPostResponse> posts;
            if (limit != null) {
                posts = postService.getAllPosts().stream().limit(limit).toList();
            } else
                posts = postService.getAllPosts();
            return ResponseEntity.ok(new ApiResponse("Success", posts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getAllPostsByUser(@PathVariable Long userId) {
        try {
            List<GetPostResponse> posts = postService.getAllPostsByUser(userId);
            return ResponseEntity.ok(new ApiResponse("Success", posts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @GetMapping("/id/{postId}")
    public ResponseEntity<ApiResponse> getPostById(@PathVariable Long postId) {
        try {
            GetPostResponse post = postService.getPostById(postId);
            return ResponseEntity.ok(new ApiResponse("Success", post));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @PostMapping("/comment/{postId}")
    public ResponseEntity<ApiResponse> addComment(@PathVariable Long postId, @RequestBody AddCommentRequest commentRequest) {
        try {
            Comment createdComment = commentService.createCommentForPost(postId, commentRequest);
            return ResponseEntity.ok(new ApiResponse("Comment added successfully", createdComment));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error adding comment", e.getMessage()));
        }
    }
    @GetMapping("/comment/{postId}")
    public ResponseEntity<ApiResponse> getAllCommentsForPost(@PathVariable Long postId) {
        try {
            List<Comment> comments = commentService.getAllCommentsForPost(postId);
            return ResponseEntity.ok(new ApiResponse("Success", comments));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
        }
    }
    @PostMapping("/image/{postId}")
    public ResponseEntity<ApiResponse> addImage(@PathVariable Long postId, @RequestParam("files") List<MultipartFile> files) {
        try {
            List<ImageDto> savedImages = imageService.saveImages(files, postId);
            return ResponseEntity.ok(new ApiResponse("Images added successfully", savedImages));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error adding images", e.getMessage()));
        }
    }
    @GetMapping("/image/{postId}")
    public ResponseEntity<ApiResponse> getImageByPostId(@PathVariable Long postId) {
        try {
            List<Image> images = imageService.getImagesByPostId(postId);
            return ResponseEntity.ok(new ApiResponse("Success", images));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
        }
    }

}
