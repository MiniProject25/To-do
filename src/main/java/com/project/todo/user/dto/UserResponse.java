package com.project.todo.user.dto;

import com.project.todo.user.User;

public class UserResponse {
    private final String username;
    private final String email;

    public UserResponse(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public static UserResponse from(User user) {
        return new UserResponse(user.getUsername(),  user.getEmail());
    }
}