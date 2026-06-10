package io.github.blairjin.user_order_api.domain.user.vo;

import io.github.blairjin.user_order_api.exception.BAD_REQUEST.InvalidValueException;

import java.util.Objects;

public record LoginId(
        String value
) {
    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 32;

    public LoginId{
        if (value == null) {
            throw new InvalidValueException("LoginId", "Login ID cannot be null.");
        }

        if(value.isBlank()){
            throw new InvalidValueException("LoginId", "Login ID cannot be blank.");
        }

        if(value.length()<MIN_LENGTH){
            throw new InvalidValueException("LoginId", "Login ID must be at least 5 characters.");
        }

        if(value.length()>MAX_LENGTH){
            throw new InvalidValueException("LoginId", "Login ID must not exceed 32 characters.");
        }
    }

    public static LoginId of(String value){
        return new LoginId(value);
    }
}