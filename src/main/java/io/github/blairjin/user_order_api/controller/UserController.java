package io.github.blairjin.user_order_api.controller;

import io.github.blairjin.user_order_api.application.user.usecase.*;
import io.github.blairjin.user_order_api.dto.user.*;
import io.github.blairjin.user_order_api.infrastructure.jwt.CustomUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final GetUserProfileUseCase getUserProfileUseCase;
    private final UpdateUserEmailUseCase updateUserEmailUseCase;
    private final UpdateUserProfileUseCase updateUserProfileUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody UserCreateRequest request){
        createUserUseCase.execute(request);
    }

    @PostMapping("/address")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAddress(@AuthenticationPrincipal CustomUserPrincipal principal){
    }

    @GetMapping("/me")
    public UserResponse get(@AuthenticationPrincipal CustomUserPrincipal principal){
        return getUserUseCase.execute(principal.userId());
    }

    @GetMapping("/me/profile")
    public UserProfileResponse getProfile(@AuthenticationPrincipal CustomUserPrincipal principal){
        return getUserProfileUseCase.execute(principal.userId());
    }

    @GetMapping("/me/address")
    public void getAddress(@AuthenticationPrincipal CustomUserPrincipal principal){ }

    @PatchMapping("/me/email")
    public void update(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @Valid @RequestBody UserEmailUpdateRequest request
    ){
        updateUserEmailUseCase.execute(principal.userId(), request);
    }

    @PatchMapping("/me/profile")
    public void updateProfile(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @Valid @RequestBody UserProfileUpdateRequest request
    ){
        updateUserProfileUseCase.execute(principal.userId(), request);
    }

    @PatchMapping("/me/address")
    public void updateAddress(@AuthenticationPrincipal CustomUserPrincipal principal){ }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal CustomUserPrincipal principal){
        deleteUserUseCase.execute(principal.userId());
    }
}