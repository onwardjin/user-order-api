package com.example.userorder.dto;

import jakarta.validation.constraints.NotBlank;

public class UserUpdateRequestDto {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Age is required")
    private Integer age;

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
