package com.example.userorder.domain.order.vo;

import com.example.userorder.common.NumberValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderQuantity {
    @Column(nullable = false)
    private int orderQuantity;

    private OrderQuantity(int orderQuantity) {
        NumberValidator.validatePositive(orderQuantity);
        this.orderQuantity = orderQuantity;
    }

    public static OrderQuantity of(int orderQuantity) {
        return new OrderQuantity(orderQuantity);
    }

    public int value() {
        return orderQuantity;
    }
}