package io.github.blairjin.user_order_api.domain.user.vo;

import io.github.blairjin.user_order_api.exception.BAD_REQUEST.InvalidValueException;

import java.util.Objects;

public record EncodedPassword(
        String value
) {
    public EncodedPassword{
        if (value == null) {
            throw new InvalidValueException("EncodedPassword", "Encoded password cannot be null.");
        }

        if(value.isBlank()){
            throw new InvalidValueException("EncodedPassword", "Encoded password cannot be blank");
        }
    }

    public static EncodedPassword of(String value){
        return new EncodedPassword(value);
    }
}