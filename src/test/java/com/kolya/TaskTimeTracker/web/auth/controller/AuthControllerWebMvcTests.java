package com.kolya.TaskTimeTracker.web.auth.controller;

import com.kolya.TaskTimeTracker.auth.controller.AuthController;
import com.kolya.TaskTimeTracker.auth.dto.AuthResponse;
import com.kolya.TaskTimeTracker.auth.dto.LoginRequest;
import com.kolya.TaskTimeTracker.auth.dto.RegisterRequest;
import com.kolya.TaskTimeTracker.auth.service.AuthService;
import com.kolya.TaskTimeTracker.common.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerWebMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    @WithMockUser
    void register_shouldReturn200_withToken() throws Exception {
        RegisterRequest request = new RegisterRequest("user1", "password1");
        AuthResponse response = new AuthResponse("some.jwt.token");

        when(authService.register(request)).thenReturn(response);

        mockMvc.perform(post("/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("some.jwt.token"));
    }

    @Test
    @WithMockUser
    void login_shouldReturn200_withToken() throws Exception {
        LoginRequest request = new LoginRequest("user1", "password1");
        AuthResponse response = new AuthResponse("some.jwt.token");

        when(authService.login(request)).thenReturn(response);

        mockMvc.perform(post("/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("some.jwt.token"));
    }

    @Test
    @WithMockUser
    void register_shouldReturn400_whenInvalidBody() throws Exception {
        RegisterRequest request = new RegisterRequest("ab", "123");

        mockMvc.perform(post("/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void login_shouldReturn400_whenBlankFields() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"\",\"password\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}
