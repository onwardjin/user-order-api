package com.example.userorder.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderUpdateRequestDto(
        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be greater than 0")
        Integer quantity,

        @NotNull(message = "Unit Price is required")
        @Positive(message = "Unit price must be greater than 0")
        Integer unitPrice
) {
}