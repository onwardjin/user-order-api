package com.example.userorder.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank(message = "Login ID is required")
        String loginId,
        @NotBlank(message = "Password is required")
        String password
) {
}