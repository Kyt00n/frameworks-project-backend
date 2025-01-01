package com.kytoon.frameworks_project_backend.service.post;

import com.kytoon.frameworks_project_backend.model.Post;
import com.kytoon.frameworks_project_backend.request.AddPostRequest;

import java.util.List;

public interface IPostService {
    Post createPost(AddPostRequest post);
    Post getPostById(Long id);
    List<Post> getAllPostsByUser(Long userId);
    List<Post> getAllPosts();
}
