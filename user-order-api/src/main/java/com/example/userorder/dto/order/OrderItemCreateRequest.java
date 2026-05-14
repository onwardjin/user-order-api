package com.example.userorder.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemCreateRequest(
        @NotNull(message = "Product ID is required")
        Long productId,
        
        @Positive
        int quantity
) {
}