package com.kytoon.frameworks_project_backend.request;

import lombok.Data;

@Data
public class AddCommentRequest {
    private String content;
    private Long userId;
}
