package io.github.blairjin.user_order_api.application.cart.command;

import io.github.blairjin.user_order_api.domain.order.vo.OrderQuantity;

public record CartItemCreateCommand(
        Long productId,
        OrderQuantity quantity
) {
}