package com.example.userorder.dto.order;

import com.example.userorder.entity.Order;
import com.example.userorder.entity.OrderStatus;

public record OrderResponseDto(
        Long userId,
        Long orderId,
        Long productId,
        OrderStatus orderStatus,
        String productName,
        Integer quantity,
        Integer unitPrice,
        Integer totalPrice

) {
    public static OrderResponseDto from(Order order) {
        return new OrderResponseDto(
                order.getUser().getId(),
                order.getId(),
                order.getProduct().getId(),
                order.getOrderStatus(),
                order.getProduct().getName(),
                order.getQuantity(),
                order.getUnitPrice(),
                order.getTotalPrice()
        );
    }
}