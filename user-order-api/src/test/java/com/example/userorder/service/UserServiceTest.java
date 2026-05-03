package com.example.userorder.service;

import com.example.userorder.dto.*;
import com.example.userorder.entity.User;
import com.example.userorder.exception.DuplicateLoginIdException;
import com.example.userorder.exception.InvalidLoginException;
import com.example.userorder.exception.UserNotFoundException;
import com.example.userorder.repository.UserRepository;
import com.example.userorder.security.JwtProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    private static final Long USER_ID = 1L;
    private static final String VALID_NAME = "testUserName";
    private static final int VALID_AGE = 20;
    private static final String VALID_LOGIN_ID = "validLoginId";
    private static final String RAW_PASSWORD = "validPassword";
    private static final String ENCODED_PASSWORD = "encodedPassword";
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String UPDATE_NAME = "UpdatedName";
    private static final int UPDATE_AGE = 30;

    private User createSavedUser() {
        return User.createUser(
                VALID_NAME,
                VALID_AGE,
                VALID_LOGIN_ID,
                ENCODED_PASSWORD
        );
    }

    @Test
    void createUser_success() {
        UserCreateRequestDto request =
                new UserCreateRequestDto(
                        VALID_NAME,
                        VALID_AGE,
                        VALID_LOGIN_ID,
                        RAW_PASSWORD
                );
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        when(userRepository.findByLoginId(VALID_LOGIN_ID))
                .thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.password()))
                .thenReturn(ENCODED_PASSWORD);
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserResponseDto result = userService.createUser(request);

        verify(userRepository).findByLoginId(VALID_LOGIN_ID);
        verify(passwordEncoder).encode(RAW_PASSWORD);
        verify(userRepository).save(captor.capture());

        assertEquals(VALID_NAME, result.name());
        assertEquals(VALID_AGE, result.age());
        assertEquals(VALID_LOGIN_ID, result.loginId());

        User capturedUser = captor.getValue();
        assertEquals(VALID_NAME, capturedUser.getName());
        assertEquals(VALID_AGE, capturedUser.getAge());
        assertEquals(VALID_LOGIN_ID, capturedUser.getLoginId());
        assertEquals(ENCODED_PASSWORD, capturedUser.getPassword());
    }

    @Test
    void createUser_duplicateLoginId_throwsException() {
        UserCreateRequestDto request =
                new UserCreateRequestDto(
                        VALID_NAME,
                        VALID_AGE,
                        VALID_LOGIN_ID,
                        RAW_PASSWORD
                );
        User savedUser = createSavedUser();

        when(userRepository.findByLoginId(VALID_LOGIN_ID))
                .thenReturn(Optional.of(savedUser));

        assertThrows(DuplicateLoginIdException.class,
                () -> userService.createUser(request));

        verify(userRepository).findByLoginId(VALID_LOGIN_ID);
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void createUser_dbConstrainViolation_throwsDulpicateLoginIdException() {
        UserCreateRequestDto request =
                new UserCreateRequestDto(
                        VALID_NAME,
                        VALID_AGE,
                        VALID_LOGIN_ID,
                        RAW_PASSWORD
                );

        when(userRepository.findByLoginId(VALID_LOGIN_ID))
                .thenReturn(Optional.empty());
        when(passwordEncoder.encode(RAW_PASSWORD))
                .thenReturn(ENCODED_PASSWORD);
        when(userRepository.save(any(User.class)))
                .thenThrow(DataIntegrityViolationException.class);

        assertThrows(DuplicateLoginIdException.class,
                () -> userService.createUser(request));

        verify(userRepository).findByLoginId(VALID_LOGIN_ID);
        verify(passwordEncoder).encode(RAW_PASSWORD);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void login_success() {
        LoginRequestDto request = new LoginRequestDto(VALID_LOGIN_ID, RAW_PASSWORD);
        User savedUser = createSavedUser();

        when(userRepository.findByLoginId(VALID_LOGIN_ID))
                .thenReturn(Optional.of(savedUser));
        when(passwordEncoder.matches(RAW_PASSWORD, ENCODED_PASSWORD))
                .thenReturn(true);
        when(jwtProvider.createToken(VALID_LOGIN_ID))
                .thenReturn(ACCESS_TOKEN);

        LoginResponseDto response = userService.login(request);

        verify(userRepository).findByLoginId(VALID_LOGIN_ID);
        verify(passwordEncoder).matches(RAW_PASSWORD, ENCODED_PASSWORD);
        verify(jwtProvider).createToken(VALID_LOGIN_ID);

        assertEquals(ACCESS_TOKEN, response.token());
    }

    @Test
    void login_userNotFound_throwsException() {
        LoginRequestDto request = new LoginRequestDto(VALID_LOGIN_ID, RAW_PASSWORD);

        when(userRepository.findByLoginId(VALID_LOGIN_ID))
                .thenReturn(Optional.empty());

        assertThrows(InvalidLoginException.class,
                () -> userService.login(request));

        verify(userRepository).findByLoginId(VALID_LOGIN_ID);
        verify(passwordEncoder, never()).matches(any(), any());
        verify(jwtProvider, never()).createToken(any());
    }

    @Test
    void login_wrongPassword_throwsException() {
        LoginRequestDto request = new LoginRequestDto(VALID_LOGIN_ID, RAW_PASSWORD);
        User savedUser = createSavedUser();

        when(userRepository.findByLoginId(VALID_LOGIN_ID))
                .thenReturn(Optional.of(savedUser));
        when(passwordEncoder.matches(RAW_PASSWORD, ENCODED_PASSWORD))
                .thenReturn(false);

        assertThrows(InvalidLoginException.class,
                () -> userService.login(request));

        verify(userRepository).findByLoginId(VALID_LOGIN_ID);
        verify(passwordEncoder).matches(RAW_PASSWORD, ENCODED_PASSWORD);
        verify(jwtProvider, never()).createToken(any());
    }

    @Test
    void updateUser_success() {
        UserUpdateRequestDto request = new UserUpdateRequestDto(UPDATE_NAME, UPDATE_AGE);
        User savedUser = createSavedUser();

        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.of(savedUser));

        UserResponseDto result = userService.updateUser(USER_ID, request);

        verify(userRepository).findById(USER_ID);

        assertEquals(UPDATE_NAME, result.name());
        assertEquals(UPDATE_AGE, result.age());
        assertEquals(UPDATE_NAME, savedUser.getName());
        assertEquals(UPDATE_AGE, savedUser.getAge());
    }

    @Test
    void updateUser_userNotFound_throwsException() {
        UserUpdateRequestDto request = new UserUpdateRequestDto(UPDATE_NAME, UPDATE_AGE);

        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.updateUser(USER_ID, request));

        verify(userRepository).findById(USER_ID);
    }

    @Test
    void deleteUser_success() {
        User savedUser = createSavedUser();

        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.of(savedUser));

        userService.deleteUser(USER_ID);

        verify(userRepository).findById(USER_ID);
        verify(userRepository).delete(savedUser);
    }

    @Test
    void deleteUser_userNotFound_throwsException() {
        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.deleteUser(USER_ID));

        verify(userRepository).findById(USER_ID);
        verify(userRepository, never()).delete(any());
    }
}