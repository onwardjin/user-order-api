package com.example.userorder.dto.order;

import com.example.userorder.entity.OrderStatus;

public record OrderSearchCondition(
        String productName,
        OrderStatus status
) {
}