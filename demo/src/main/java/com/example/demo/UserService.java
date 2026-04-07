package com.example.demo;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    // CREATE
    public UserResponseDto createUser(UserRequestDto request){
        User user = new User(request.getName(), request.getAge());
        User savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser.getName(), savedUser.getAge());
    }

    // READ ALL
    public List<UserResponseDto> readAllUser(){
        List<User> users = userRepository.findAll();
        List<UserResponseDto> result = new ArrayList<>();

        for(User u:users){
            result.add(new UserResponseDto(u.getName(), u.getAge()));
        }

        return result;
    }

    // READ ONE
    public UserResponseDto readUser(long id){
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isPresent()){
            User u = optionalUser.get();
            return new UserResponseDto(u.getName(), u.getAge());
        }
        return null;
    }

    // UPDATE
    public UserResponseDto updateUser(long id, UserRequestDto request){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User u = optionalUser.get();
            u.setName(request.getName());
            u.setAge(request.getAge());

            User updateUser = userRepository.save(u);
            return new UserResponseDto(updateUser.getName(), updateUser.getAge());
        }
        return null;
    }

    // DELETE
    public void deleteUser(long id){
        userRepository.deleteById(id);
    }
}