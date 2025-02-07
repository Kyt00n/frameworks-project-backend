package com.kytoon.frameworks_project_backend.service.post;

import com.kytoon.frameworks_project_backend.model.Post;
import com.kytoon.frameworks_project_backend.request.AddPostRequest;
import com.kytoon.frameworks_project_backend.response.GetPostResponse;

import java.util.List;

public interface IPostService {
    Post createPost(AddPostRequest post);
    GetPostResponse getPostById(Long id);
    List<GetPostResponse> getAllPostsByUser(Long userId);
    List<GetPostResponse> getAllPosts();
}
