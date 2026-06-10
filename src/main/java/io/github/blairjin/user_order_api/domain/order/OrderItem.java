package io.github.blairjin.user_order_api.domain.order;

import io.github.blairjin.user_order_api.application.order.command.OrderItemCommand;
import io.github.blairjin.user_order_api.domain.common.BaseTimeEntity;
import io.github.blairjin.user_order_api.domain.common.vo.Money;
import io.github.blairjin.user_order_api.domain.order.vo.OrderQuantity;
import io.github.blairjin.user_order_api.domain.product.vo.ProductName;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@Table(name = "order_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long orderId;

    @Column(nullable = false, updatable = false)
    private Long productId;

    // SNAPSHOT
    @Column(nullable = false, updatable = false)
    private String productName;

    @Column(nullable = false, updatable = false)
    private int orderQuantity;

    @Column(nullable = false, updatable = false)
    private long unitPrice;

    @Column(nullable = false, updatable = false)
    private long totalPrice;

    private OrderItem(Long orderId, Long productId, ProductName productName, OrderQuantity orderQuantity, Money unitPrice){
        this.orderId = Objects.requireNonNull(orderId);
        this.productId = Objects.requireNonNull(productId);
        this.productName = Objects.requireNonNull(productName).value();
        this.orderQuantity = Objects.requireNonNull(orderQuantity).value();
        this.unitPrice = Objects.requireNonNull(unitPrice).value();
        this.totalPrice = calculateTotalPrice(unitPrice, orderQuantity);
    }

    public static OrderItem create(Long orderId, OrderItemCommand command){
        return new OrderItem(
                orderId,
                command.productId(),
                command.productName(),
                command.orderQuantity(),
                command.unitPrice()
        );
    }

    private static long calculateTotalPrice(Money unitPrice, OrderQuantity orderQuantity){
        return unitPrice.value() * orderQuantity.value();
    }
}