package com.kytoon.frameworks_project_backend.service.post;

import com.kytoon.frameworks_project_backend.model.Image;
import com.kytoon.frameworks_project_backend.model.Post;
import com.kytoon.frameworks_project_backend.model.User;
import com.kytoon.frameworks_project_backend.repository.PostRepository;
import com.kytoon.frameworks_project_backend.repository.UserRepository;
import com.kytoon.frameworks_project_backend.request.AddPostRequest;
import com.kytoon.frameworks_project_backend.response.GetPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    public GetPostResponse getPostById(Long id) {
        return postRepository.findById(id).map(this::convertToResponse).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    @Override
    public List<GetPostResponse> getAllPostsByUser(Long userId) {
        return postRepository.findByUserId(userId).stream().map(this::convertToResponse).toList();
    }

    @Override
    public List<GetPostResponse> getAllPosts() {
        return postRepository.findAll().stream().map(this::convertToResponse).toList();
    }

    private Post savePost(AddPostRequest request){
        Post post = new Post();
        User user = userRepository.getUserById(request.getUserId());
        post.setText(request.getText());
        post.setUser(user);
        return postRepository.save(post);
    }

    private GetPostResponse convertToResponse(Post post){
        var imagesUrls = Optional.ofNullable(post.getImages())
                .orElse(Collections.emptyList())
                .stream()
                .map(Image::getDownloadUrl)
                .toList();
        return new GetPostResponse(post.getId(), String.format("post %d", post.getId()), post.getText(), post.getUser().getId(), imagesUrls, post.getComments());
    }
}
