package com.example.demo;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController{

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    // CREATE
    @PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto request){
        return userService.createUser(request);
    }

    // READ ALL
    @GetMapping
    public List<UserResponseDto> readAllUser(){
        return userService.readAllUser();
    }

    // READ ONE
    @GetMapping("/{id}")
    public UserResponseDto readUser(@PathVariable long id){
        return userService.readUser(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable long id, @RequestBody UserRequestDto request){
        return userService.updateUser(id, request);
    }

    // DELETE
    @DeleteMapping
    public void deleteUser(@PathVariable long id){
        userService.deleteUser(id);
    }
}