package com.project.todo.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue
    private UUID userId;

    @NotBlank(message = "Username is required")
    private String username;

    @Email(message = "Invalid Email")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 4, max = 50)
    private String password;

    protected User() {}

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    // getters
    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
