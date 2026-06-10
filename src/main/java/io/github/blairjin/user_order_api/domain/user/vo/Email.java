package io.github.blairjin.user_order_api.domain.user.vo;

import io.github.blairjin.user_order_api.exception.BAD_REQUEST.InvalidValueException;

import java.util.Objects;

public record Email(
        String value
) {
    public Email{
        if (value == null) {
            throw new InvalidValueException("Email", "Email cannot be null.");
        }

        if(value.isBlank()){
            throw new InvalidValueException("Email", "Email cannot be blank.");
        }

        if(!value.contains("@")){
            throw new InvalidValueException("Email", "Invalid email format");
        }
    }

    public static Email of(String value){
        return new Email(value);
    }
}