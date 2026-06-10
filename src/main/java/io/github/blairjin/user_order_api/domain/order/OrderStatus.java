package io.github.blairjin.user_order_api.domain.order;

import java.util.EnumSet;
import java.util.Set;

public enum OrderStatus {
    PAYMENT_COMPLETED,
    PREPARING,
    SHIPPING,
    DELIVERED,
    CANCELED;

    private Set<OrderStatus> nextStatuses;

    static {
        PAYMENT_COMPLETED.nextStatuses = EnumSet.of(PREPARING, CANCELED);
        PREPARING.nextStatuses = EnumSet.of(SHIPPING, CANCELED);
        SHIPPING.nextStatuses = EnumSet.of(DELIVERED);
        DELIVERED.nextStatuses = EnumSet.noneOf(OrderStatus.class);
        CANCELED.nextStatuses = EnumSet.noneOf(OrderStatus.class);
    }

    public boolean canChangeTo(OrderStatus status) {
        return nextStatuses.contains(status);
    }
}