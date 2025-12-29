package com.project.todo.user;

import com.project.todo.user.dto.UserLogin;
import com.project.todo.user.dto.UserRequest;
import com.project.todo.user.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String login(@RequestBody @Valid UserLogin userLogin) {
        return userService.login(userLogin);
    }
}