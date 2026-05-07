package com.example.userorder.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductCreateRequestDto(
        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Unit price is required")
        @PositiveOrZero(message = "Unit price must be zero or positive")
        Integer unitPrice,

        @NotNull(message = "Stock quantity is required")
        @PositiveOrZero(message = "Stock quantity must be zero or positive")
        Integer stockQuantity
) {
}