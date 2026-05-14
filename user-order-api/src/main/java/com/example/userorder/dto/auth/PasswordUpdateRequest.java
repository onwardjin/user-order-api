package com.example.userorder.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record PasswordUpdateRequest(
        @NotBlank(message = "Password is required")
        String oldPassword,

        @NotBlank(message = "Password is required")
        String newPassword
) {
}