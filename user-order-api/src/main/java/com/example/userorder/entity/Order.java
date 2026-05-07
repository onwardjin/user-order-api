package com.example.userorder.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private Order(User user, Product product, Integer quantity, OrderStatus orderStatus) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = product.getUnitPrice() * quantity;
        this.orderStatus = orderStatus;
        this.createdAt = LocalDateTime.now();
    }

    public static Order createOrder(User user, Product product, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new RuntimeException();
        }

        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException();
        }

        product.decreaseStock(quantity);
        return new Order(user, product, quantity, OrderStatus.CREATED);
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}