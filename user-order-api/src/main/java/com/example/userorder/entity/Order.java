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

    // 기본 정보
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;

    // 상태
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    // 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 로그
    private LocalDateTime createdAt;


    private Order(Product product, User user, Integer quantity) {
        if (product == null) {
            throw new IllegalArgumentException("상품은 필수입니다.");
        }

        if (user == null) {
            throw new IllegalArgumentException("사용자는 필수입니다.");
        }

        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("수량은 1 이상이여야 합니다.");
        }

        if (product.getPrice() == null || product.getPrice() < 0) {
            throw new IllegalArgumentException("가격은 0 이상이어야 합니다.");
        }

        product.decreaseStock(quantity); // 재고 선 확보

        this.product = product;
        this.user = user;
        this.quantity = quantity;
        this.unitPrice = product.getPrice();
        this.totalPrice = quantity * unitPrice;
        this.orderStatus = OrderStatus.ORDERED;
        this.createdAt = LocalDateTime.now();
    }

    public static Order createOrder(Product product, User user, Integer quantity) {
        return new Order(product, user, quantity);
    }

    public void cancel() {
        if (this.orderStatus == OrderStatus.CANCELED) {
            throw new IllegalArgumentException("이미 취소된 주문입니다.");
        }

        if (this.orderStatus == OrderStatus.COMPLETED) {
            throw new IllegalArgumentException("완료된 주문은 취소할 수 없습니다.");
        }

        this.orderStatus = OrderStatus.CANCELED;
        this.product.increaseStock(this.quantity);
    }
}
