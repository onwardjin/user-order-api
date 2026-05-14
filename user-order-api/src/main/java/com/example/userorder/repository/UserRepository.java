package com.example.userorder.repository;

import com.example.userorder.domain.user.User;
import com.example.userorder.domain.user.vo.LoginId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByLoginId(LoginId loginId);

    Optional<User> findByLoginId(LoginId loginId);
}