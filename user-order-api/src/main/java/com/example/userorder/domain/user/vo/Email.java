package com.example.userorder.domain.user.vo;

import com.example.userorder.common.StringValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {
    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 255;

    @Column(length = MAX_LENGTH)
    private String email;

    private Email(String email) {
        StringValidator.validate(email, MIN_LENGTH, MAX_LENGTH);
        validateEmailFormat(email);

        this.email = email;
    }

    public static Email of(String email) {
        return new Email(email);
    }

    public String value() {
        return email;
    }

    private static void validateEmailFormat(String email) {
        if (!email.contains("@")) {
            throw new IllegalArgumentException();
        }
    }
}