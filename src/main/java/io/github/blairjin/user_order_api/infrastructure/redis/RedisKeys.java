package io.github.blairjin.user_order_api.infrastructure.redis;

import io.github.blairjin.user_order_api.domain.user.vo.LoginId;

public final class RedisKeys {
    private static final String LOGIN_FAIL_PREFIX = "auth:login:fail:";
    private static final String REFRESH_TOKEN_PREFIX = "auth:refresh:";
    private static final String ACCESS_TOKEN_BLACKLIST_PREFIX = "auth:access:blacklist:";
    private static final String EMAIL_VERIFICATION_PREFIX = "auth:email:";

    public static String loginFail(LoginId loginId){
        return LOGIN_FAIL_PREFIX + loginId.value();
    }

    public static String refreshToken(Long userId){
        return REFRESH_TOKEN_PREFIX + userId;
    }

    public static String accessTokenBlacklist(String accessToken){
        return ACCESS_TOKEN_BLACKLIST_PREFIX + accessToken;
    }

    public static String emailVerification(Long userId){
        return EMAIL_VERIFICATION_PREFIX + userId;
    }
}