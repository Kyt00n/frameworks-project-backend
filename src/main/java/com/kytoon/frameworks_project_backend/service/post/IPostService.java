package com.kytoon.frameworks_project_backend.service.post;

import com.kytoon.frameworks_project_backend.request.AddPostRequest;
import com.kytoon.frameworks_project_backend.response.GetPostResponse;

import java.util.List;

public interface IPostService {
    void createPost(AddPostRequest post);
    GetPostResponse getPostById(Long id);
    List<GetPostResponse> getAllPostsByUser(Long userId);
    List<GetPostResponse> getAllPosts();
    void likePost(Long postId, Long userId);
    void unlikePost(Long postId, Long userId);
    void deletePost(Long postId);
}
