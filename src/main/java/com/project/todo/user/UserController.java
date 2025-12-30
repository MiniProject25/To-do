package com.project.todo.user;

import com.project.todo.user.dto.UserLogin;
import com.project.todo.user.dto.UserRequest;
import com.project.todo.user.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody @Valid UserRequest userRequest) {
        return userService.register(userRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLogin userLogin) {
        String jwt = userService.login(userLogin);

        return ResponseEntity.ok(Map.of("jwt", jwt));
    }

    @GetMapping("/me")
    public UserResponse me() {
        UserResponse user = (UserResponse) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            return userService.getUserById(user.getUserID());
        }
        return null;
    }

    @PutMapping("/update")
    public UserResponse update(@RequestBody UserRequest userRequest) {
        UserResponse user = (UserResponse) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            return userService.updateUser(user.getUserID(), userRequest);
        }
        return null;
    }

    @DeleteMapping("/delete")
    public UserResponse delete() {
        UserResponse user = (UserResponse) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            return userService.deleteUser(user.getUserID());
        }
        return null;
    }

}