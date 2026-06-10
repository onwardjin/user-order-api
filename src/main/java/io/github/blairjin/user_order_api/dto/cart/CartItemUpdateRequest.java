package io.github.blairjin.user_order_api.dto.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemUpdateRequest(
        @NotNull @Positive
        Integer quantity
) {
}