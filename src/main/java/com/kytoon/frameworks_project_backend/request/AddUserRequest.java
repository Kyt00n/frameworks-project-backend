package com.kytoon.frameworks_project_backend.request;

import com.kytoon.frameworks_project_backend.enums.UserType;
import lombok.Data;

@Data
public class AddUserRequest {
    private String username;
    private String password;
    private String email;
    private UserType userType;
}
