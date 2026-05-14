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
public class LoginId {
    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 20;

    @Column(name = "login_id", nullable = false, length = MAX_LENGTH)
    private String loginId;

    private LoginId(String loginId) {
        StringValidator.validate(loginId, MIN_LENGTH, MAX_LENGTH);
        this.loginId = loginId;
    }

    public static LoginId of(String loginId) {
        return new LoginId(loginId);
    }

    public String value() {
        return loginId;
    }
}