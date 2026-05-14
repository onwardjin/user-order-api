package com.example.userorder.domain.user;

import com.example.userorder.domain.common.BaseTimeEntity;
import com.example.userorder.domain.user.vo.*;
import com.example.userorder.dto.user.UserProfileRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;
import java.util.Optional;

@Entity
@Getter
@Table(
        name = "users",
        uniqueConstraints = {@UniqueConstraint(name = "uk_users_login_id", columnNames = "login_id")}
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private LoginId loginId;

    @Embedded
    private Password password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_id")
    private UserProfile profile;

    private User(LoginId loginId, Password password, Role role) {
        this.loginId = Objects.requireNonNull(loginId);
        this.password = Objects.requireNonNull(password);
        this.role = Objects.requireNonNull(role);
    }

    public static User createUserWithProfile(LoginId loginId, Password password, UserName name, BirthDate birthDate, Email email) {

        User user = createUser(loginId, password);
        if (name != null || birthDate != null || email != null) {
            user.profile = UserProfile.createUserProfile(name, birthDate, email);
        }

        return user;
    }

    private static User createUser(LoginId loginId, Password password) {
        return new User(loginId, password, Role.USER);
    }

    private static User createAdmin(LoginId loginId, Password password) {
        return new User(loginId, password, Role.ADMIN);
    }

    public Optional<UserProfile> getProfile() {
        return Optional.ofNullable(this.profile);
    }

    public boolean isPasswordMatcher(PasswordEncoder passwordEncoder, String rawPassword) {
        return passwordEncoder.matches(rawPassword, password.value());
    }

    public void updatePassword(Password password) {
        this.password = password;
    }

    public void updateProfile(UserName name, BirthDate birthDate, Email email) {
        if (this.profile == null) {
            this.profile = UserProfile.createUserProfile(name, birthDate, email);
            return;
        }
        this.profile.updateProfile(name, birthDate, email);
    }
}