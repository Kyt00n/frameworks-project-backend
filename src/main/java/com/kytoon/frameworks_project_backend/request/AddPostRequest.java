package com.kytoon.frameworks_project_backend.request;

import lombok.Data;

@Data
public class AddPostRequest {
    private String text;
    private Long userId;
}
