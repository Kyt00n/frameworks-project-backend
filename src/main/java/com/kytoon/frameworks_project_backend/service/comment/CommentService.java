package com.kytoon.frameworks_project_backend.service.comment;

import com.kytoon.frameworks_project_backend.model.Comment;
import com.kytoon.frameworks_project_backend.model.Post;
import com.kytoon.frameworks_project_backend.repository.CommentRepository;
import com.kytoon.frameworks_project_backend.repository.PostRepository;
import com.kytoon.frameworks_project_backend.repository.UserRepository;
import com.kytoon.frameworks_project_backend.request.AddCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public Comment createCommentForPost(Long postId, AddCommentRequest comment) {
        Comment newComment = new Comment();
        Post post = postRepository.getPostById(postId);
        newComment.setPost(post);
        newComment.setContent(comment.getContent());
        newComment.setUser(userRepository.findById(comment.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        return commentRepository.save(newComment);
    }

    @Override
    public List<Comment> getAllCommentsForPost(Long postId) {
        return commentRepository.findByPostId(postId);
    }

}
