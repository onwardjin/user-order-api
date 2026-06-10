package io.github.blairjin.user_order_api.domain.user.vo;

import io.github.blairjin.user_order_api.exception.BAD_REQUEST.InvalidValueException;

import java.util.Objects;

public record PhoneNumber(
        String value
) {
    private static final int MIN_LENGTH = 9;
    private static final int MAX_LENGTH = 12;

    public PhoneNumber{
        if (value == null) {
            throw new InvalidValueException(
                    "PhoneNumber", "Phone number cannot be null."
            );
        }

        if (value.isBlank()) {
            throw new InvalidValueException(
                    "PhoneNumber", "Phone number cannot be blank."
            );
        }

        if (value.length() < MIN_LENGTH) {
            throw new InvalidValueException(
                    "PhoneNumber", "Phone number must be at least 9 characters long."
            );
        }

        if (value.length() > MAX_LENGTH) {
            throw new InvalidValueException(
                    "PhoneNumber", "Phone number must not exceed 12 characters."
            );
        }
    }

    public static PhoneNumber of(String value){
        return new PhoneNumber(value);
    }
}