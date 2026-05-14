package com.example.userorder.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringValidator {
    private static final int DEFAULT_MIN_LENGTH = 0;
    private static final int DEFAULT_MAX_LENGTH = 255;

    public static void validate(String value) {
        validate(value, DEFAULT_MIN_LENGTH, DEFAULT_MAX_LENGTH);
    }

    public static void validate(String value, int min) {
        validate(value, min, DEFAULT_MAX_LENGTH);
    }

    public static void validate(String value, int min, int max) {
        if (min < 0 || max < 0 || min > max) {
            throw new IllegalArgumentException();
        }
        
        validateNotNull(value);
        validateTrimmed(value);
        validateMaxLength(value, max);

        if (min > 0) {
            validateNotBlank(value);
            validateMinLength(value, min);
        }
    }

    private static void validateNotNull(String value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
    }

    private static void validateNotBlank(String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException();
        }
    }

    private static void validateTrimmed(String value) {
        if (!value.equals(value.trim())) {
            throw new IllegalArgumentException();
        }
    }

    private static void validateMinLength(String value, int min) {
        if (value.length() < min) {
            throw new IllegalArgumentException();
        }
    }

    private static void validateMaxLength(String value, int max) {
        if (value.length() > max) {
            throw new IllegalArgumentException();
        }
    }
}