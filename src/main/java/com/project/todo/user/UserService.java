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
            throw new RuntimeException("Incorrect password");
        }

        return jwtUtil.generateToken(foundUser.getUserId(), foundUser.getEmail());
    }

    // logout

    // delete

    // data
    public UserResponse getUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return UserResponse.from(user);
    }

}
