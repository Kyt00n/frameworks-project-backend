package com.kytoon.frameworks_project_backend.controller;

import com.kytoon.frameworks_project_backend.exception.UserNotFoundException;
import com.kytoon.frameworks_project_backend.model.User;
import com.kytoon.frameworks_project_backend.request.AddUserRequest;
import com.kytoon.frameworks_project_backend.response.ApiResponse;
import com.kytoon.frameworks_project_backend.response.LoginUserResponse;
import com.kytoon.frameworks_project_backend.service.user.IUserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final IUserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signUp(@RequestBody AddUserRequest user) {
        try{
            User foundUser = userService.createUser(user);
            return ResponseEntity.ok(new ApiResponse("Success", foundUser));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Error", e.getMessage()));
        }
    }
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse> signIn(@RequestBody AddUserRequest user, HttpServletResponse response) {
        try{
            User foundUser = userService.getUserByUsername(user.getUsername());
            if (!foundUser.getPassword().equals(user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Error", "Invalid password"));
            }
            byte[] keyBytes = "your_256_bit_base64_encoded_secret_key".getBytes(StandardCharsets.UTF_8);
            Key key = Keys.hmacShaKeyFor(keyBytes);
            String accessToken = Jwts.builder()
                    .subject(foundUser.getUsername())
                    .signWith(key)
                    .compact();

            // Set token in HttpOnly cookie
            Cookie cookie = new Cookie("accessToken", accessToken);
            cookie.setHttpOnly(true);
            cookie.setSecure(true); // Ensure the cookie is only sent over HTTPS
            cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60); // 1 week
            response.addCookie(cookie);
            return ResponseEntity.ok(new ApiResponse("Success", new LoginUserResponse(foundUser, accessToken)));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error", e.getMessage()));
        }
    }

}
