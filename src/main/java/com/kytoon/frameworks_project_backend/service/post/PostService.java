package com.kytoon.frameworks_project_backend.service.post;

import com.kytoon.frameworks_project_backend.model.Image;
import com.kytoon.frameworks_project_backend.model.Post;
import com.kytoon.frameworks_project_backend.model.User;
import com.kytoon.frameworks_project_backend.repository.PostRepository;
import com.kytoon.frameworks_project_backend.repository.UserRepository;
import com.kytoon.frameworks_project_backend.request.AddPostRequest;
import com.kytoon.frameworks_project_backend.response.GetCommentResponse;
import com.kytoon.frameworks_project_backend.response.GetPostResponse;
import com.kytoon.frameworks_project_backend.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService{
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    @Override
    public void createPost(AddPostRequest post) {
        savePost(post);
    }

    @Override
    public GetPostResponse getPostById(Long id) {
        return postRepository.findById(id).map(this::convertToResponse).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    @Override
    public List<GetPostResponse> getAllPostsByUser(Long userId) {
        return postRepository.findByUserId(userId, Sort.by(Sort.Direction.DESC, "id")).stream().map(this::convertToResponse).toList();
    }

    @Override
    public List<GetPostResponse> getAllPosts() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream().map(this::convertToResponse).toList();
    }

    @Override
    public void likePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        if (!post.getLikedUserIds().contains(userId)) {
            post.getLikedUserIds().add(userId);
            postRepository.save(post);
        }
    }

    @Override
    public void unlikePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        if (post.getLikedUserIds().contains(userId)) {
            post.getLikedUserIds().remove(userId);
            postRepository.save(post);
        }
    }
    public List<Long> getUsersWhoLikedPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        return post.getLikedUserIds() != null ? post.getLikedUserIds() : Collections.emptyList();
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    private Post savePost(AddPostRequest request){
        Post post = new Post();
        User user = userRepository.getUserById(request.getUserId());
        post.setText(request.getText());
        post.setUser(user);
        post.setDate(new Date().toString());
        return postRepository.save(post);
    }

    private GetPostResponse convertToResponse(Post post){
        var imagesUrls = Optional.ofNullable(post.getImages())
                .orElse(Collections.emptyList())
                .stream()
                .map(Image::getDownloadUrl)
                .toList();
        List<GetCommentResponse> comments = Optional.ofNullable(post.getComments())
                .orElse(Collections.emptyList())
                .stream()
                .map(comment -> new GetCommentResponse(
                        comment.getId(),
                        comment.getContent(),
                        Optional.ofNullable(comment.getUser())
                                .map(User::getUsername)
                                .orElse("Unknown User"), post.getId(),
                        comment.getDate()
                ))
                .toList();
        var likedUserIds = getUsersWhoLikedPost(post.getId());
        return new GetPostResponse(post.getId(), post.getUser().getUsername(), post.getText(), post.getUser().getId(), imagesUrls, comments,likedUserIds, post.getDate() );
    }
}
