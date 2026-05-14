package com.example.userorder.domain.common.vo;

import com.example.userorder.common.NumberValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money {
    @Column(nullable = false)
    private long amount;

    private Money(long amount) {
        NumberValidator.validateNonNegative(amount);
        this.amount = amount;
    }

    public static Money of(long amount) {
        return new Money(amount);
    }

    public long value() {
        return amount;
    }

    public static Money zero() {
        return Money.of(0);
    }

    public Money add(Money money) {
        long result = this.amount + money.value();
        return Money.of(result);
    }

    public Money multiply(int quantity) {
        long result = this.amount * quantity;
        return Money.of(result);
    }
}