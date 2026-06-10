package io.github.blairjin.user_order_api.dto.user;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record UserCreateRequest(
        @NotBlank(message = "Login ID is required")
        String loginId,

        @NotBlank(message = "Password is required")
        String password,

        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Username is required")
        String userName,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String phoneNumber
) {
}