package com.example.userorder.controller;

import com.example.userorder.dto.auth.LoginRequest;
import com.example.userorder.dto.auth.LoginResponse;
import com.example.userorder.dto.auth.PasswordUpdateRequest;
import com.example.userorder.dto.user.UserCreateRequest;
import com.example.userorder.dto.user.UserProfileRequest;
import com.example.userorder.dto.user.UserResponse;
import com.example.userorder.security.CustomUserPrincipal;
import com.example.userorder.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createUser(@Valid @RequestBody UserCreateRequest request) {
        return userService.createUser(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @GetMapping("/me")
    public UserResponse getUser(@AuthenticationPrincipal CustomUserPrincipal principal) {
        return userService.getUser(principal.getId());
    }

    @PatchMapping("/me/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @Valid @RequestBody PasswordUpdateRequest request
    ) {
        userService.updatePassword(principal.getId(), request);
    }

    @PatchMapping("/me/profile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProfile(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @Valid @RequestBody UserProfileRequest request
    ) {
        userService.updateProfile(principal.getId(), request);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        userService.deleteUser(principal.getId());
    }
}