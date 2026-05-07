package com.example.userorder.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User(String loginId, String password, String name, Integer age, Role role) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.age = age;
        this.role = role;
        this.createdAt = LocalDateTime.now();
    }

    public static User createUser(String loginId, String encodedPassword, String name, Integer age) {
        return new User(
                loginId,
                encodedPassword,
                name,
                age,
                Role.USER
        );
    }

    public static User createAdmin(String loginId, String encodedPassword, String name, Integer age) {
        return new User(
                loginId,
                encodedPassword,
                name,
                age,
                Role.ADMIN
        );
    }

    public void updateInfo(String newName, String encodedPassword) {
        this.name = newName;
        this.password = encodedPassword;
        this.updatedAt = LocalDateTime.now();
    }
}