package com.example.demo;

import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    // 가짜 DB, 학습용
    private List<User> users = new ArrayList<>();

    public UserResponseDto createUser(UserRequestDto request){
        User user = new User(request.getName(), request.getAge());
        users.add(user);
        return new UserResponseDto(user.getName(), user.getAge());
    }

    public List<UserResponseDto> getUsers(){
        List<UserResponseDto> tmpUser = new ArrayList<>();
        for(User u:users){
            tmpUser.add(new UserResponseDto(u.getName(), u.getAge()));
        }
        return tmpUser;
    }
}

/*
- UserController가 받은 요청을 실제로 처리
- 내부 객체 생성
- 응답 객체로 변환
- 컨트롤러는 얇게 두고 서비스에 처리 로직을 넘김
 */