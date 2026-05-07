package com.example.userorder.dto.user;

import com.example.userorder.entity.User;

public record UserResponseDto(
        Long id,
        String name,
        Integer age
) {
    public static UserResponseDto from(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getAge()
        );
    }
}