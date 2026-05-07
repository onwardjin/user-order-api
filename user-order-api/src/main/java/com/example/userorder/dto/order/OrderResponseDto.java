package com.example.userorder.dto.order;

import com.example.userorder.entity.Order;
import com.example.userorder.entity.OrderStatus;

import java.time.LocalDateTime;

public record OrderResponseDto(
        Long id,
        Long productId,
        String productName,
        Integer quantity,
        Integer totalPrice,
        OrderStatus orderStatus,
        LocalDateTime createdAt
) {
    public static OrderResponseDto from(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getProduct().getId(),
                order.getProduct().getName(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getOrderStatus(),
                order.getCreatedAt()
        );
    }
}