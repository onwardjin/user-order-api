package com.example.userorder.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;

    @Column(unique = true)
    private String loginId;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    private User(String name, Integer age, String loginId, String password, Role role) {
        this.name = name;
        this.age = age;
        this.loginId = loginId;
        this.password = password;
        this.role = role;

    }

    // 일반 유저 생성
    public static User createUser(String name, Integer age, String loginId, String password) {
        return new User(name, age, loginId, password, Role.USER);
    }

    // 관리자 유저 생성
    public static User createAdmin(String name, Integer age, String loginId, String password) {
        return new User(name, age, loginId, password, Role.ADMIN);
    }

    // 유저 기본정보 변경
    public void updateProfile(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}