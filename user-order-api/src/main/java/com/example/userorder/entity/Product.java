package com.example.userorder.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer unitPrice;

    @Column(nullable = false)
    private Integer stockQuantity;

    private Product(String name, Integer unitPrice, Integer stockQuantity) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.stockQuantity = stockQuantity;
    }

    public static Product createProduct(String name, Integer unitPrice, Integer stockQuantity) {
        validatePositiveOrZero(unitPrice);
        validatePositiveOrZero(stockQuantity);

        return new Product(name, unitPrice, stockQuantity);
    }

    public void increaseStock(Integer quantity) {
        validatePositive(quantity);

        this.stockQuantity += quantity;
    }

    public void decreaseStock(Integer quantity) {
        int remainingStock = stockQuantity - quantity;
        validatePositive(quantity);
        validatePositiveOrZero(remainingStock);

        this.stockQuantity -= quantity;
    }

    public void updateProduct(String name, Integer unitPrice, Integer stockQuantity) {
        validateNullAndBlank(name);
        validatePositiveOrZero(unitPrice);
        validatePositiveOrZero(stockQuantity);

        this.name = name;
        this.unitPrice = unitPrice;
        this.stockQuantity = stockQuantity;
    }

    private static void validateNullAndBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new RuntimeException();
        }
    }

    private static void validatePositiveOrZero(Integer value) {
        if (value == null || value < 0) {
            throw new RuntimeException();
        }
    }

    private static void validatePositive(Integer value) {
        if (value == null || value <= 0) {
            throw new RuntimeException();
        }
    }
}