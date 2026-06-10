package io.github.blairjin.user_order_api.dto.order;

import io.github.blairjin.user_order_api.domain.order.Order;
import io.github.blairjin.user_order_api.domain.order.OrderStatus;

public record OrderResponse(
        Long id,
        long totalPrice,
        OrderStatus orderStatus
) {
    public static OrderResponse from(Order order){
        return new OrderResponse(
              order.getId(),
              order.getTotalPrice(),
              order.getOrderStatus()
        );
    }
}