package io.github.blairjin.user_order_api.domain.user.vo;

import io.github.blairjin.user_order_api.exception.BAD_REQUEST.InvalidValueException;

import java.util.Objects;

public record UserName(
        String value
) {
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 20;

    public UserName{
        if (value == null) {
            throw new InvalidValueException(
                    "UserName", "User name cannot be null."
            );
        }

        if (value.isBlank()) {
            throw new InvalidValueException(
                    "UserName", "User name cannot be blank."
            );
        }

        if (value.length() < MIN_LENGTH) {
            throw new InvalidValueException(
                    "UserName", "User name must be at least 2 characters long."
            );
        }

        if (value.length() > MAX_LENGTH) {
            throw new InvalidValueException(
                    "UserName", "User name must not exceed 20 characters."
            );
        }
    }

    public static UserName of(String value){
        return new UserName(value);
    }
}