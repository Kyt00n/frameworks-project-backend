package com.kytoon.frameworks_project_backend.response;

import com.kytoon.frameworks_project_backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserResponse {
    private User user;
    private String accessToken;
}
