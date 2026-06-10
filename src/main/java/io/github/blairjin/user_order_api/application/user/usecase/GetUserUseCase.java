package io.github.blairjin.user_order_api.application.user.usecase;

import io.github.blairjin.user_order_api.application.user.reader.UserReader;
import io.github.blairjin.user_order_api.domain.user.User;
import io.github.blairjin.user_order_api.dto.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUserUseCase {
    private final UserReader userReader;

    public UserResponse execute(Long userId){
        User user = userReader.getUserById(userId);
        return UserResponse.from(user);
    }
}