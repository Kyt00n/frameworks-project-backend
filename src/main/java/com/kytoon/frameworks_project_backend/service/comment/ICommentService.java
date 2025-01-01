package com.kytoon.frameworks_project_backend.service.comment;

import com.kytoon.frameworks_project_backend.model.Comment;
import com.kytoon.frameworks_project_backend.request.AddCommentRequest;

import java.util.List;

public interface ICommentService {
    Comment createCommentForPost(Long postId, AddCommentRequest comment);
    List<Comment> getAllCommentsForPost(Long postId);
}
