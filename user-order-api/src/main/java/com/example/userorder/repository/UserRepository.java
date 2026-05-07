package com.example.userorder.repository;

import com.example.userorder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByLoginId(String loginId);

    Optional<User> findByLoginId(String loginId);

    @Modifying
    @Query("DELETE FROM User u WHERE u.id = :userId")
    int deleteByUserId(@Param("userId") Long userId);
}
