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
public class UserName {
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 50;

    @Column(length = MAX_LENGTH)
    private String name;

    private UserName(String name) {
        StringValidator.validate(name, MIN_LENGTH, MAX_LENGTH);
        this.name = name;
    }

    public static UserName of(String name) {
        return new UserName(name);
    }

    public String value() {
        return name;
    }
}