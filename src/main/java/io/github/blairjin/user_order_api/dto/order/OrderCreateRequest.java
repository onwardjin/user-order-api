package io.github.blairjin.user_order_api.dto.order;

import jakarta.validation.constraints.NotNull;

public record OrderCreateRequest(
        @NotNull
        Long productId,

        @NotNull
        Integer quantity
) {
}