package com.project.todo.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**

 Represents a user registration request.*
 @param username the unique username chosen by the user
 @param email the user's email address
 @param password the user's raw password (should be hashed before storage)*/
// public record UserRequest(
//        String username,
//        String email,
//        String password
// ) {}

public class UserRequest {
    @NotBlank(message = "Username is required")
    private final String username;
    @Email(message = "Invalid Email")
    @NotBlank(message = "Email is required")
    private final String email;
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 4, max = 50)
    private final String password;

    public UserRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
