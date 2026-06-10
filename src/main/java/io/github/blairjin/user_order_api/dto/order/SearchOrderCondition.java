package io.github.blairjin.user_order_api.dto.order;

import io.github.blairjin.user_order_api.domain.order.OrderStatus;

import java.time.LocalDate;

public record SearchOrderCondition(
        Long minPrice,
        Long maxPrice,
        OrderStatus orderStatus,
        LocalDate startDate,
        LocalDate endDate
) {
}