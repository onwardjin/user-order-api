package com.example.userorder.domain.product.vo;

import com.example.userorder.common.NumberValidator;
import com.example.userorder.domain.order.vo.OrderQuantity;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockQuantity {
    @Column(nullable = false)
    private int stockQuantity;

    private StockQuantity(int stockQuantity) {
        NumberValidator.validateNonNegative(stockQuantity);
        this.stockQuantity = stockQuantity;
    }

    public static StockQuantity of(int stockQuantity) {
        return new StockQuantity(stockQuantity);
    }

    public int value() {
        return stockQuantity;
    }

    public StockQuantity increaseStock(RestockQuantity restockQuantity) {
        int remainingStock = stockQuantity + restockQuantity.value();
        return StockQuantity.of(remainingStock);
    }

    public StockQuantity decreaseStock(OrderQuantity orderQuantity) {
        int remainingStock = stockQuantity - orderQuantity.value();
        return StockQuantity.of(remainingStock);
    }
}