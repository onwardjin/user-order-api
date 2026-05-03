package com.example.userorder.controller;

import com.example.userorder.dto.*;
import com.example.userorder.exception.InvalidLoginException;
import com.example.userorder.exception.UserNotFoundException;
import com.example.userorder.service.UserService;
import com.example.userorder.support.WithMockCustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
public class UserControllerTest {
    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private static final Long USER_ID = 1L;
    private static final String NAME = "Steve";
    private static final int AGE = 20;
    private static final String LOGIN_ID = "testLoginId";
    private static final String PASSWORD = "testPassword";
    private static final String UPDATE_NAME = "UpdatedName";
    private static final int UPDATE_AGE = 30;

    @Test
    @WithMockUser
    void createUser_success() throws Exception {
        UserCreateRequestDto request = new UserCreateRequestDto(NAME, AGE, LOGIN_ID, PASSWORD);
        UserResponseDto response = new UserResponseDto(USER_ID, NAME, AGE, LOGIN_ID);

        when(userService.createUser(any(UserCreateRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(post("/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.age").value(AGE))
                .andExpect(jsonPath("$.loginId").value(LOGIN_ID));
        verify(userService).createUser(any(UserCreateRequestDto.class));
    }

    @Test
    @WithMockUser
    void createUser_invalidRequest_returnsBadRequest() throws Exception {
        UserCreateRequestDto request = new UserCreateRequestDto(NAME, -1, LOGIN_ID, PASSWORD);

        mockMvc.perform(post("/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
        verify(userService, never()).createUser(any(UserCreateRequestDto.class));
    }

    @Test
    @WithMockUser
    void login_success() throws Exception {
        LoginRequestDto request = new LoginRequestDto(LOGIN_ID, PASSWORD);
        LoginResponseDto response = new LoginResponseDto("createdToken");

        when(userService.login(any(LoginRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(post("/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("createdToken"));
        verify(userService).login(request);
    }

    @Test
    @WithMockUser
    void login_invalidCredentials_returnsUnauthorized() throws Exception {
        LoginRequestDto request = new LoginRequestDto(LOGIN_ID, PASSWORD);

        when(userService.login(any(LoginRequestDto.class)))
                .thenThrow(new InvalidLoginException());

        mockMvc.perform(post("/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
        verify(userService).login(any(LoginRequestDto.class));
    }

    @Test
    @WithMockCustomUser
    void updateUser_success() throws Exception {
        UserUpdateRequestDto request = new UserUpdateRequestDto(UPDATE_NAME, UPDATE_AGE);
        UserResponseDto response = new UserResponseDto(USER_ID, UPDATE_NAME, UPDATE_AGE, LOGIN_ID);

        when(userService.updateUser(USER_ID, any(UserUpdateRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(patch("/users/me")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.name").value(UPDATE_NAME))
                .andExpect(jsonPath("$.age").value(UPDATE_AGE))
                .andExpect(jsonPath("$.loginId").value(LOGIN_ID));
        verify(userService).updateUser(USER_ID, any(UserUpdateRequestDto.class));
    }

    @Test
    @WithMockCustomUser
    void updateUser_userNotFound_returnsNotFound() throws Exception {
        UserUpdateRequestDto request = new UserUpdateRequestDto(UPDATE_NAME, UPDATE_AGE);

        when(userService.updateUser(eq(USER_ID), any(UserUpdateRequestDto.class)))
                .thenThrow(UserNotFoundException.class);

        mockMvc.perform(patch("/users/me")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
        verify(userService).updateUser(eq(USER_ID), any(UserUpdateRequestDto.class));
    }

    @Test
    @WithMockCustomUser
    void updateUser_invalidRequest_returnsBadRequest() throws Exception {
        UserUpdateRequestDto request = new UserUpdateRequestDto(UPDATE_NAME, -1);

        mockMvc.perform(patch("/users/me")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
        verify(userService, never()).updateUser(any(), any());
    }

    @Test
    @WithMockCustomUser
    void deleteUser_success() throws Exception {
        mockMvc.perform(delete("/users/me")
                        .with(csrf()))
                .andExpect(status().isNoContent());
        verify(userService).deleteUser(USER_ID);
    }

    @Test
    @WithMockCustomUser
    void deleteUser_userNotFound_returnsNotFound() throws Exception {
        doThrow(UserNotFoundException.class)
                .when(userService)
                .deleteUser(USER_ID);

        mockMvc.perform(delete("/users/me")
                        .with(csrf()))
                .andExpect(status().isNotFound());
        verify(userService).deleteUser(USER_ID);
    }
}