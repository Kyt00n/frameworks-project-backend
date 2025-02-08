package com.kytoon.frameworks_project_backend.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetCommentResponse {
    private Long id;
    private String content;
    private String email;
    private Long postId;
    private String date;
}
