package io.github.blairjin.user_order_api.application.user.usecase;

import io.github.blairjin.user_order_api.application.user.reader.UserReader;
import io.github.blairjin.user_order_api.domain.user.UserProfile;
import io.github.blairjin.user_order_api.dto.user.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUserProfileUseCase {
    private final UserReader userReader;

    public UserProfileResponse execute(Long userId){
        UserProfile profile = userReader.getUserProfileByUserId(userId);
        return UserProfileResponse.from(profile);
    }
}