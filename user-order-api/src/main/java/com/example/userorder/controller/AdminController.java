package com.example.userorder.controller;

import com.example.userorder.dto.UserResponseDto;
import com.example.userorder.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String testAdmin(){
        return "Admin :)";
    }

    @GetMapping("/users")
    public List<UserResponseDto> getUsers(){
        return userService.getAllUsers();
    }
}
