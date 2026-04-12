package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;

@Service
public class UserService{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponseDto createUser(UserRequestDto request){
        User user = new User(request.getName(), request.getAge());
        User savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser);
    }

    public List<UserResponseDto> getAllUsers(){
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .toList();
    }

    public UserResponseDto getUserById(Long id){
        return userRepository.findById(id)
                .map(UserResponseDto::new)
                .orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto request){
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        user.setName(request.getName());
        user.setAge(request.getAge());

        return new UserResponseDto(user);
    }

    public void deleteUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }
}