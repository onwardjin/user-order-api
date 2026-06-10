package io.github.blairjin.user_order_api.dto.user;

import io.github.blairjin.user_order_api.domain.user.UserProfile;

import java.time.LocalDate;

public record UserProfileResponse(
        String userName,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String phoneNumber
) {
    public static UserProfileResponse from(UserProfile profile){
        return new UserProfileResponse(
                profile.getUserName(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getBirthDate(),
                profile.getPhoneNumber()
        );
    }
}