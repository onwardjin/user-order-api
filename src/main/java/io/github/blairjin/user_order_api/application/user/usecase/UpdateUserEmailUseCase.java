package io.github.blairjin.user_order_api.application.user.usecase;

import io.github.blairjin.user_order_api.application.user.service.UserCommandService;
import io.github.blairjin.user_order_api.domain.user.vo.Email;
import io.github.blairjin.user_order_api.dto.user.UserEmailUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserEmailUseCase {
    private final UserCommandService userCommandService;

    public void execute(Long userId, UserEmailUpdateRequest request){
        Email email = Email.of(request.email());
        userCommandService.update(userId, email);
    }
}