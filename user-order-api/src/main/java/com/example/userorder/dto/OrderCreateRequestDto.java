package com.example.userorder.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderCreateRequestDto(
        @NotBlank(message = "Item is required")
        String item,

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be greater than 0")
        Integer quantity,

        @NotNull(message = "Unit Price is required")
        @Positive(message = "Unit price must be greater than 0")
        Integer unitPrice
) {
}