package com.example.userorder.dto;

import jakarta.validation.constraints.*;

public record UserUpdateRequestDto(
        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Age is required")
        @PositiveOrZero(message = "Age must be at least 0")
        @Max(value = 150, message = "Age must be less than or equal to 150")
        Integer age
) {
}