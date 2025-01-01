package com.kytoon.frameworks_project_backend.service.user;

import com.kytoon.frameworks_project_backend.model.User;
import com.kytoon.frameworks_project_backend.request.AddUserRequest;

public interface IUserService {
    User getUserById(Long id);
    User getUserByEmail(String email);
    User createUser(AddUserRequest user);
}
