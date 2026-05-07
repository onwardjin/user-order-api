package com.example.userorder.service;

import com.example.userorder.dto.auth.LoginRequestDto;
import com.example.userorder.dto.auth.LoginResponseDto;
import com.example.userorder.dto.user.UserCreateRequestDto;
import com.example.userorder.dto.user.UserResponseDto;
import com.example.userorder.dto.user.UserUpdateRequestDto;
import com.example.userorder.entity.User;
import com.example.userorder.exception.DuplicateLoginIdException;
import com.example.userorder.exception.InvalidCurrentPasswordException;
import com.example.userorder.exception.InvalidLoginException;
import com.example.userorder.exception.UserNotFoundException;
import com.example.userorder.repository.UserRepository;
import com.example.userorder.security.JwtProvider;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public UserResponseDto createUser(UserCreateRequestDto request) {
        if (userRepository.existsByLoginId(request.loginId())) {
            throw new DuplicateLoginIdException("Login ID already exists");
        }

        User user = User.createUser(
                request.loginId(),
                passwordEncoder.encode(request.password()),
                request.name(),
                request.age()
        );

        try {
            User savedUser = userRepository.save(user);
            return UserResponseDto.from(savedUser);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateLoginIdException("Login ID already exists");
        }
    }

    public LoginResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByLoginId(request.loginId())
                .orElseThrow(InvalidLoginException::new);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidLoginException();
        }

        String token = jwtProvider.createToken(request.loginId());
        return new LoginResponseDto(token);
    }

    public UserResponseDto getMyInfo(Long userId) {
        return UserResponseDto.from(findUserById(userId));
    }

    @Transactional
    public UserResponseDto updateInfo(Long userId, UserUpdateRequestDto request) {
        User user = findUserById(userId);

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new InvalidCurrentPasswordException();
        }

        String encodedPassword = passwordEncoder.encode(request.newPassword());
        user.updateInfo(request.name(), encodedPassword);

        return UserResponseDto.from(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        int deletedCount = userRepository.deleteByUserId(userId);

        if (deletedCount == 0) {
            throw new UserNotFoundException();
        }
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

}