package com.example.userorder.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NumberValidator {

    public static void validatePositive(long value) {
        validateMin(value, 1);
    }

    public static void validateNonNegative(long value) {
        validateMin(value, 0);
    }

    public static void validateRange(long value, long min, long max) {
        if (min < 0 || max < 0 || min > max) {
            throw new IllegalArgumentException();
        }

        validateMin(value, min);
        validateMax(value, max);
    }

    private static void validateMin(long value, long min) {
        if (value < min) {
            throw new IllegalArgumentException();
        }
    }

    private static void validateMax(long value, long max) {
        if (value > max) {
            throw new IllegalArgumentException();
        }
    }
}