package io.github.blairjin.user_order_api.application.user.usecase;

import io.github.blairjin.user_order_api.application.user.command.UserCommandMapper;
import io.github.blairjin.user_order_api.application.user.command.UserProfileUpdateCommand;
import io.github.blairjin.user_order_api.application.user.service.UserCommandService;
import io.github.blairjin.user_order_api.dto.user.UserProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserProfileUseCase {
    private final UserCommandService userCommandService;

    public void execute(Long userId, UserProfileUpdateRequest request){
        UserProfileUpdateCommand command = UserCommandMapper.toUpdateCommand(request);
        userCommandService.updateProfile(userId, command);
    }
}