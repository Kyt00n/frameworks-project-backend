package com.kytoon.frameworks_project_backend.controller;

import com.kytoon.frameworks_project_backend.exception.UserNotFoundException;
import com.kytoon.frameworks_project_backend.model.User;
import com.kytoon.frameworks_project_backend.request.AddUserRequest;
import com.kytoon.frameworks_project_backend.response.ApiResponse;
import com.kytoon.frameworks_project_backend.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final IUserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signUp(@RequestBody AddUserRequest user) {
        try{
            User newUser = userService.createUser(user);
            return ResponseEntity.ok(new ApiResponse("Success", newUser));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Error", e.getMessage()));
        }
    }
}
