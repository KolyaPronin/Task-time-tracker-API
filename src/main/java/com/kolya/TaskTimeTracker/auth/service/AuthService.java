package com.kolya.TaskTimeTracker.auth.service;

import com.kolya.TaskTimeTracker.auth.dto.AuthResponse;
import com.kolya.TaskTimeTracker.auth.dto.LoginRequest;
import com.kolya.TaskTimeTracker.auth.dto.RegisterRequest;
import com.kolya.TaskTimeTracker.auth.persistence.UserMapper;
import com.kolya.TaskTimeTracker.common.security.JwtService;
import com.kolya.TaskTimeTracker.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserMapper userMapper,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userMapper.findByUsername(request.username()).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        userMapper.insert(user);
        return new AuthResponse(jwtService.generateToken(user));
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        User user = userMapper.findByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return new AuthResponse(jwtService.generateToken(user));
    }
}
