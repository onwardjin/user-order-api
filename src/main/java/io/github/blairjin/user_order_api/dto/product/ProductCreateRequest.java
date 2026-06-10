package io.github.blairjin.user_order_api.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductCreateRequest(
        @NotBlank
        String productName,

        @NotNull
        @PositiveOrZero
        Integer stockQuantity,

        @NotNull
        @PositiveOrZero
        Long unitPrice
) {
}