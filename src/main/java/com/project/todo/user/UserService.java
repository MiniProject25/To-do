package com.project.todo.user;

import com.project.todo.jwtUtil.JwtUtil;
import com.project.todo.user.dto.UserLogin;
import com.project.todo.user.dto.UserRequest;
import com.project.todo.user.dto.UserResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // services
    // register
    public UserResponse register(UserRequest userRequest) {
        String encrypted_pwd =  bCryptPasswordEncoder.encode(userRequest.getPassword());
        User user = new User(userRequest.getEmail(), userRequest.getUsername(), encrypted_pwd);
        userRepository.save(user);
        return UserResponse.from(user);
    }

    // login
    public String login(UserLogin userLogin) {
        User foundUser = userRepository.findByEmail(userLogin.email());

        if (foundUser == null) {
            throw new EntityNotFoundException("User not found");
        }

        if (!bCryptPasswordEncoder.matches(userLogin.password(), foundUser.getPassword())) {
            throw new RuntimeException("Incorrect email or password");
        }

        return jwtUtil.generateToken(foundUser.getUserId(), foundUser.getEmail());
    }

    // data
    public UserResponse getUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return UserResponse.from(user);
    }

    public User getUser(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    // update
    public UserResponse updateUser(UUID id, UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

        // updating email
        if (!userRequest.getEmail().isEmpty()) {
            user.setEmail(userRequest.getEmail());
        }

        // updating pwd
        if (!userRequest.getUsername().isEmpty()) {
            user.setUsername(userRequest.getUsername());
        }

        // updating username
        if (!userRequest.getPassword().isEmpty() && bCryptPasswordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        }

        userRepository.save(user);

        return UserResponse.from(user);
    }

    public UserResponse deleteUser(UUID id) {
        UserResponse user = UserResponse.from(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
        userRepository.deleteById(id);

        return user;
    }

}
