package com.example.userorder.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String item;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Order(String item, Integer quantity, Integer unitPrice, User user) {
        this.item = item;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = quantity * unitPrice;
        this.user = user;
    }

    // 주문 생성
    public static Order createOrder(String item, Integer quantity, Integer unitPrice, User user) {
        return new Order(item, quantity, unitPrice, user);
    }

    // 주문 변경(수량/단가)
    public void updateOrder(Integer quantity, Integer unitPrice) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = quantity * unitPrice;
    }
}
