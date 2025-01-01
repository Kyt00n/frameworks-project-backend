package com.kytoon.frameworks_project_backend.service.post;

import com.kytoon.frameworks_project_backend.model.Post;
import com.kytoon.frameworks_project_backend.model.User;
import com.kytoon.frameworks_project_backend.repository.PostRepository;
import com.kytoon.frameworks_project_backend.repository.UserRepository;
import com.kytoon.frameworks_project_backend.request.AddPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PostService implements IPostService{
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public Post createPost(AddPostRequest post) {
        return savePost(post);
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(()->
                new RuntimeException("Post not found"));
    }

    @Override
    public List<Post> getAllPostsByUser(Long userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    private Post savePost(AddPostRequest request){
        Post post = new Post();
        User user = userRepository.getUserById(request.getUserId());
        post.setText(request.getText());
        post.setUser(user);
        return postRepository.save(post);
    }
}
