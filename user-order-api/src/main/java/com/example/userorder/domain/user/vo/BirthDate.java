package com.example.userorder.domain.user.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BirthDate {
    private static final int MINIMUM_AGE = 14;

    private LocalDate birthDate;

    private BirthDate(LocalDate birthDate) {
        validateNotNull(birthDate);
        validateNotFuture(birthDate);
        validateMinAge(birthDate);

        this.birthDate = birthDate;
    }

    public static BirthDate of(LocalDate birthDate) {
        return new BirthDate(birthDate);
    }

    public LocalDate value() {
        return birthDate;
    }

    private static void validateNotNull(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException();
        }
    }

    private static void validateNotFuture(LocalDate birthDate) {
        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException();
        }
    }

    private static void validateMinAge(LocalDate birthDate) {
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age < MINIMUM_AGE) {
            throw new IllegalArgumentException();
        }
    }
}