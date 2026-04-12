package com.example.demo.dto;

import com.example.demo.entity.User;

public class UserResponseDto{
    private Long id;
    private String name;
    private int age;

    public UserResponseDto(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.age = user.getAge();
    }

    public Long getId(){ return id; }
    public String getName(){ return name; }
    public int getAge(){ return age; }
}