package com.example.userorder.dto.user;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record UserCreateRequest(
        @NotBlank(message = "Login ID is required")
        String loginId,
        @NotBlank(message = "Password is required")
        String password,
        String name,
        LocalDate birthDate,
        String email
) {
}