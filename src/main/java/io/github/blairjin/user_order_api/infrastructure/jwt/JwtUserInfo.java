package io.github.blairjin.user_order_api.infrastructure.jwt;

import io.github.blairjin.user_order_api.domain.user.User;
import io.github.blairjin.user_order_api.domain.user.UserRole;
import io.jsonwebtoken.Claims;



public record JwtUserInfo(
        Long userId,
        String loginId,
        UserRole role
) {
    public static JwtUserInfo from(Claims claims){
        return new JwtUserInfo(
                Long.valueOf(claims.getSubject()),
                claims.get("loginId", String.class),
                UserRole.valueOf(claims.get("role", String.class))
        );
    }
    public static JwtUserInfo from(User user){
        return new JwtUserInfo(
                user.getId(),
                user.getLoginId(),
                user.getRole()
        );
    }
}