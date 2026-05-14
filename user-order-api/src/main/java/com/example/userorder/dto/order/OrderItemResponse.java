package com.example.userorder.dto.order;

import com.example.userorder.domain.order.OrderItem;

public record OrderItemResponse(
        Long id,
        String productName,
        int orderQuantity,
        long unitPrice
) {
    public static OrderItemResponse from(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getId(),
                orderItem.getProduct().getName().value(),
                orderItem.getOrderQuantity().value(),
                orderItem.getUnitPrice().value()
        );
    }
}