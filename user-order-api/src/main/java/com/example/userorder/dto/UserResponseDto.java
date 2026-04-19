package com.example.userorder.dto;

import com.example.userorder.entity.Role;
import com.example.userorder.entity.User;

public class UserResponseDto {
    private Long id;
    private String name;
    private Integer age;
    private String loginId;
    private Role role;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.age = user.getAge();
        this.loginId = user.getLoginId();
        this.role = user.getRole();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getLoginId() {
        return loginId;
    }

    public Role getRole() {
        return role;
    }
}
