package com.example.userorder.service;

import com.example.userorder.dto.LoginRequestDto;
import com.example.userorder.dto.UserCreateRequestDto;
import com.example.userorder.entity.Role;
import com.example.userorder.entity.User;
import com.example.userorder.exception.DuplicateLoginException;
import com.example.userorder.repository.UserRepository;
import com.example.userorder.security.JwtProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private UserService userService;

    @Test
    void 중복된_loginId면_예외발생(){
        UserCreateRequestDto request = new UserCreateRequestDto();
        request.setLoginId("TEST_ACCOUNT");

        User existingUser = new User(
                "name",
                20,
                "TEST_ACCOUNT",
                "password",
                Role.USER
        );

        when(userRepository.findByLoginId("TEST_ACCOUNT"))
                .thenReturn(Optional.of(existingUser));

        assertThrows(DuplicateLoginException.class, () -> {
            userService.createUser(request);
        });
    }
}