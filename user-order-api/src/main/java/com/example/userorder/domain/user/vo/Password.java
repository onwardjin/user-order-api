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
public class Password {
    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 100;

    @Column(nullable = false, length = MAX_LENGTH)
    private String password;

    private Password(String password) {
        StringValidator.validate(password, MIN_LENGTH, MAX_LENGTH);
        this.password = password;
    }

    public static Password of(String password) {
        return new Password(password);
    }

    public String value() {
        return password;
    }
}