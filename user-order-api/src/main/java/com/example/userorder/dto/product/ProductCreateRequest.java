package com.example.userorder.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductCreateRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Unit price is required")
        @Positive
        long unitPrice,

        @PositiveOrZero
        int stockQuantity
) {
}