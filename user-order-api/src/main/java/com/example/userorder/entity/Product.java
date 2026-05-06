package com.example.userorder.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 기본 정보
    @Column(nullable = false, unique = true)
    private String name;
    private Integer price;

    // 상태
    private Integer stockQuantity;

    // 로그
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Product(String name, Integer price, Integer stockQuantity) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("상품 이름은 필수입니다.");
        }

        if (price == null || price < 0) {
            throw new IllegalArgumentException("금액은 0 이상이여야 합니다.");
        }

        if (stockQuantity == null || stockQuantity < 0) {
            throw new IllegalArgumentException("재고는 0 이상이여야 합니다.");
        }

        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static Product createProduct(String name, Integer price, Integer stockQuantity) {
        return new Product(name, price, stockQuantity);
    }

    public void decreaseStock(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("수량은 1 이상이어야 합니다.");
        }

        if (this.stockQuantity < quantity) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }

        this.stockQuantity -= quantity;
    }

    public void increaseStock(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("수량은 1 이상이어야 합니다.");
        }
        this.stockQuantity += quantity;
    }

    public void update(String name, Integer price, Integer stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.updatedAt = LocalDateTime.now();
    }


}
