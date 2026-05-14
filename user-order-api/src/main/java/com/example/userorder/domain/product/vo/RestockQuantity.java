package com.example.userorder.domain.product.vo;

import com.example.userorder.common.NumberValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestockQuantity {
    @Column(nullable = false)
    private int restockQuantity;

    private RestockQuantity(int restockQuantity) {
        NumberValidator.validatePositive(restockQuantity);
        this.restockQuantity = restockQuantity;
    }

    public static RestockQuantity of(int restockQuantity) {
        return new RestockQuantity(restockQuantity);
    }

    public int value() {
        return restockQuantity;
    }
}