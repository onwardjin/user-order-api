package com.example.userorder.service;

import com.example.userorder.dto.*;
import com.example.userorder.entity.Role;
import com.example.userorder.entity.User;
import com.example.userorder.exception.DuplicateLoginException;
import com.example.userorder.exception.InvalidLoginException;
import com.example.userorder.exception.UserNotFoundException;
import com.example.userorder.repository.UserRepository;
import com.example.userorder.security.JwtProvider;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public UserResponseDto createUser(UserCreateRequestDto request){
        if(userRepository.findByLoginId(request.getLoginId()).isPresent()){
            throw new DuplicateLoginException();
        }

        User user = new User(
                request.getName(),
                request.getAge(),
                request.getLoginId(),
                passwordEncoder.encode(request.getPassword()),
                Role.USER
        );

        User savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser);
    }

    public LoginResponseDto login(LoginRequestDto request){
        User user = userRepository.findByLoginId(request.getLoginId())
                .orElseThrow(InvalidLoginException::new);

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new InvalidLoginException();
        }
        String token = jwtProvider.createToken(request.getLoginId());
        return new LoginResponseDto(token);
    }

    public UserResponseDto getInfoByLoginId(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return new UserResponseDto(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponseDto> getAllUsers(){
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .toList();
    }

    @Transactional
    public UserResponseDto updateUser(Long userId, UserUpdateRequestDto requestDto){
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        user.setName(requestDto.getName());
        user.setAge(requestDto.getAge());

        return new UserResponseDto(user);
    }

    @Transactional
    public void deleteUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }
}