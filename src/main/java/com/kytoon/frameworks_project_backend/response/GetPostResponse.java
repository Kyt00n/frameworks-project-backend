package com.kytoon.frameworks_project_backend.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPostResponse {
    private Long id;
    private String title;
    private String body;
    private Long userId;
    private List<String> imageUrls;
    private List<GetCommentResponse> comments;
    private List<Long> likes;
    private String date;
}
