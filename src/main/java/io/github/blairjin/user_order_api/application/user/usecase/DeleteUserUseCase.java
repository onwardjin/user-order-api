package io.github.blairjin.user_order_api.application.user.usecase;

import io.github.blairjin.user_order_api.application.user.service.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteUserUseCase {
    private final UserCommandService userCommandService;

    public void execute(Long userId){
        userCommandService.delete(userId);
    }
}