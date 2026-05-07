package com.example.userorder.dto.order;

import jakarta.validation.constraints.NotNull;

public record OrderCreateRequestDto(
        @NotNull(message = "Product ID is required")
        Long productId,

        @NotNull(message = "Quantity is required")
        Integer quantity
) {
}