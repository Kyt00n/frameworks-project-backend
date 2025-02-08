package com.kytoon.frameworks_project_backend.service.comment;

import com.kytoon.frameworks_project_backend.model.Comment;
import com.kytoon.frameworks_project_backend.model.Post;
import com.kytoon.frameworks_project_backend.repository.CommentRepository;
import com.kytoon.frameworks_project_backend.repository.PostRepository;
import com.kytoon.frameworks_project_backend.repository.UserRepository;
import com.kytoon.frameworks_project_backend.request.AddCommentRequest;
import com.kytoon.frameworks_project_backend.response.GetCommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@RequiredArgsConstructor
@Service
public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public void createCommentForPost(Long postId, AddCommentRequest comment) {
        Comment newComment = new Comment();
        Post post = postRepository.getPostById(postId);
        newComment.setPost(post);
        newComment.setContent(comment.getContent());
        newComment.setDate(new Date().toString());
        newComment.setUser(userRepository.findById(comment.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        commentRepository.save(newComment);
    }

    @Override
    public List<GetCommentResponse> getAllCommentsForPost(Long postId) {
        var comment =  commentRepository.findByPostId(postId);
        return comment.stream().map(c -> new GetCommentResponse(c.getId(), c.getContent(), c.getUser().getUsername(), c.getPost().getId(), c.getDate())).toList();
    }

}
