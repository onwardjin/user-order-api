package io.github.blairjin.user_order_api.repository.user;

import io.github.blairjin.user_order_api.domain.user.UserProfile;
import io.github.blairjin.user_order_api.dto.user.UserNameResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUserId(Long userId);

    @Query("SELECT up.userName FROM UserProfile up WHERE up.userId = :userId")
    Optional<String> findUserNameByUserId(Long userId);

    @Query("""
    SELECT new io.github.blairjin.user_order_api.dto.user.UserNameResult(
        up.userId,
        up.userName
    )
    FROM UserProfile up
    WHERE up.userId IN :userIds
    """)
    List<UserNameResult> findUserNamesByUserIds(List<Long> userIds);
}