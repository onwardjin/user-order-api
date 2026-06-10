package io.github.blairjin.user_order_api.domain.user.vo;

import io.github.blairjin.user_order_api.exception.BAD_REQUEST.InvalidValueException;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public record BirthDate(
        LocalDate value
) {
    private static int MIN_AGE = 14;

    public BirthDate{
        if (value == null) {
            throw new InvalidValueException("BirthDate", "Birth date cannot be null.");
        }

        if(value.isAfter(LocalDate.now())){
            throw new InvalidValueException("BirthDate", "Birth date cannot be in the future.");
        }

        int age = Period.between(value, LocalDate.now()).getYears();
        if(age < MIN_AGE){
            throw new InvalidValueException("BirthDate", "User must be at least 14 years old.");
        }
    }

    public static BirthDate of(LocalDate value){
        return new BirthDate(value);
    }
}