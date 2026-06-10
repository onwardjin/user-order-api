package io.github.blairjin.user_order_api.domain.order;

import io.github.blairjin.user_order_api.domain.common.BaseTimeEntity;
import io.github.blairjin.user_order_api.exception.BAD_REQUEST.InvalidOrderStatusException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long userId;

    @Column(nullable = false)
    private long totalPrice = 0;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private Order(Long userId){
        this.userId = Objects.requireNonNull(userId);
        this.orderStatus = OrderStatus.PAYMENT_COMPLETED;
    }

    public static Order create(Long userId){
        return new Order(userId);
    }

    public void updateTotalPrice(long totalPrice){
        this.totalPrice = totalPrice;
    }

    public void changeStatus(OrderStatus orderStatus){
        Objects.requireNonNull(orderStatus);

        if(!this.orderStatus.canChangeTo(orderStatus)){
            throw new InvalidOrderStatusException();
        }

        this.orderStatus = orderStatus;
    }
}