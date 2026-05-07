package com.example.userorder.dto.order;

import com.example.userorder.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record OrderStatusUpdateRequestDto(
        @NotNull
        OrderStatus status
) {
}