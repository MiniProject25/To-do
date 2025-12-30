package com.project.todo.user.dto;

import com.project.todo.user.User;

import java.util.UUID;

public class UserResponse {
    private final String username;
    private final String email;
    private final UUID userId;

    public UserResponse(String username, String email, UUID userId) {
        this.username = username;
        this.email = email;
        this.userId = userId;
    }

    public UUID getUserID() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public static UserResponse from(User user) {
        return new UserResponse(user.getUsername(),  user.getEmail(), user.getUserId());
    }
}