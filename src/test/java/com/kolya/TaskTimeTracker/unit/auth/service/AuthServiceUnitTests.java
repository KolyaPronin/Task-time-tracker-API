package com.kolya.TaskTimeTracker.unit.auth.service;

import com.kolya.TaskTimeTracker.auth.dto.AuthResponse;
import com.kolya.TaskTimeTracker.auth.dto.LoginRequest;
import com.kolya.TaskTimeTracker.auth.dto.RegisterRequest;
import com.kolya.TaskTimeTracker.auth.persistence.UserMapper;
import com.kolya.TaskTimeTracker.auth.service.AuthService;
import com.kolya.TaskTimeTracker.common.security.JwtService;
import com.kolya.TaskTimeTracker.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceUnitTests {

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_shouldReturnToken_whenUsernameAvailable() {
        RegisterRequest request = new RegisterRequest("newuser", "password");

        when(userMapper.findByUsername("newuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encoded");
        when(jwtService.generateToken(any(User.class))).thenReturn("jwt.token");

        AuthResponse response = authService.register(request);

        assertEquals("jwt.token", response.token());
        verify(userMapper).insert(any(User.class));
    }

    @Test
    void register_shouldThrow_whenUsernameAlreadyTaken() {
        RegisterRequest request = new RegisterRequest("existing", "password");

        when(userMapper.findByUsername("existing")).thenReturn(Optional.of(new User()));

        assertThrows(IllegalArgumentException.class, () -> authService.register(request));
        verify(userMapper, never()).insert(any());
    }

    @Test
    void login_shouldReturnToken_whenCredentialsValid() {
        LoginRequest request = new LoginRequest("user", "password");
        User user = new User();
        user.setUsername("user");

        when(userMapper.findByUsername("user")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwt.token");

        AuthResponse response = authService.login(request);

        assertEquals("jwt.token", response.token());
        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken("user", "password")
        );
    }

    @Test
    void login_shouldThrow_whenCredentialsInvalid() {
        LoginRequest request = new LoginRequest("user", "wrongpassword");

        doThrow(new BadCredentialsException("Bad credentials"))
                .when(authenticationManager).authenticate(any());

        assertThrows(BadCredentialsException.class, () -> authService.login(request));
        verify(userMapper, never()).findByUsername(any());
    }
}
