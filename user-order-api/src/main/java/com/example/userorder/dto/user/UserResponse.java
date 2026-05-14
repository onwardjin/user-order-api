package com.example.userorder.dto.user;

import com.example.userorder.domain.user.User;
import com.example.userorder.domain.user.UserProfile;

import java.time.LocalDate;

public record UserResponse(
        Long id,
        String name,
        LocalDate birthDate,
        String email
) {
    public static UserResponse from(User user) {
        UserProfile profile = user.getProfile().orElse(null);

        if (profile == null) {
            return new UserResponse(user.getId(), null, null, null);
        }

        return new UserResponse(
                user.getId(),
                profile.getName() != null
                        ? profile.getName().value()
                        : null,
                profile.getBirthDate() != null
                        ? profile.getBirthDate().value()
                        : null,
                profile.getEmail() != null
                        ? profile.getEmail().value()
                        : null
        );
    }
}