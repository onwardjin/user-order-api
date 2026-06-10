package io.github.blairjin.user_order_api.dto.user;

import io.github.blairjin.user_order_api.domain.user.User;
import io.github.blairjin.user_order_api.domain.user.UserRole;

public record UserResponse(
        String loginId,
        String email,
        UserRole role
) {
    public static UserResponse from(User user){
        return new UserResponse(user.getLoginId(), user.getEmail(), user.getRole());
    }
}