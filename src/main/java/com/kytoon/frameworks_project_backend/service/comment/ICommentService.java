package com.kytoon.frameworks_project_backend.service.comment;

import com.kytoon.frameworks_project_backend.request.AddCommentRequest;
import com.kytoon.frameworks_project_backend.response.GetCommentResponse;

import java.util.List;

public interface ICommentService {
    void createCommentForPost(Long postId, AddCommentRequest comment);
    List<GetCommentResponse> getAllCommentsForPost(Long postId);
}
