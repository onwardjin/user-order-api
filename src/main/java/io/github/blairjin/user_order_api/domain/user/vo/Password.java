package io.github.blairjin.user_order_api.domain.user.vo;

import io.github.blairjin.user_order_api.exception.BAD_REQUEST.InvalidValueException;

import java.util.Objects;

public record Password(
        String value
) {
    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 32;

    public Password{
        if (value == null) {
            throw new InvalidValueException("Password", "Password cannot be null.");
        }

        if(value.isBlank()){
            throw new InvalidValueException("Password", "Password cannot be blank.");
        }

        if(value.length()<MIN_LENGTH){
            throw new InvalidValueException("Password", "Password must be at least 5 characters long.");
        }

        if(value.length()>MAX_LENGTH){
            throw new InvalidValueException("Password", "Password must not exceed 32 characters.");
        }
    }

    public static Password of(String value){
        return new Password(value);
    }
}