package com.example.userorder.controller;

import com.example.userorder.dto.*;
import com.example.userorder.security.CustomUserPrincipal;
import com.example.userorder.service.UserService;
import jakarta.validation.Valid;
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
    public UserResponseDto create(@Valid @RequestBody UserCreateRequestDto request){
        return userService.createUser(request);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@Valid @RequestBody LoginRequestDto request){
        return userService.login(request);
    }

    @GetMapping("/me")
    public UserResponseDto getInfo(@AuthenticationPrincipal CustomUserPrincipal user){
        return userService.getInfoByLoginId(user.getId());
    }

    @PutMapping("/me")
    public UserResponseDto update(
            @AuthenticationPrincipal CustomUserPrincipal user,
            @Valid @RequestBody UserUpdateRequestDto request
            ){
        return userService.updateUser(user.getId(), request);
    }

    @DeleteMapping("/me")
    public void delete(@AuthenticationPrincipal CustomUserPrincipal user){
        userService.deleteUser(user.getId());
    }
}