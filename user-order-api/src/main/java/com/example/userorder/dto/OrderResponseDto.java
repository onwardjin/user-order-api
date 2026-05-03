package com.example.userorder.dto;

import com.example.userorder.entity.Order;

public record OrderResponseDto(
        Long id,
        String item,
        Integer quantity,
        Integer unitPrice,
        Integer totalPrice
) {
    public static OrderResponseDto from(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getItem(),
                order.getQuantity(),
                order.getUnitPrice(),
                order.getTotalPrice()
        );
    }
}