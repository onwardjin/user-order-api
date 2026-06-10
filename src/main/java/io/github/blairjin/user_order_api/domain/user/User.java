package io.github.blairjin.user_order_api.domain.user;

import io.github.blairjin.user_order_api.application.user.command.UserCreateCommand;
import io.github.blairjin.user_order_api.domain.common.BaseTimeEntity;
import io.github.blairjin.user_order_api.domain.user.vo.Email;
import io.github.blairjin.user_order_api.domain.user.vo.EncodedPassword;
import io.github.blairjin.user_order_api.domain.user.vo.LoginId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String encodedPassword;

    @Column(nullable = false, updatable = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private User(LoginId loginId, EncodedPassword encodedPassword, Email email, UserRole role){
        this.loginId = Objects.requireNonNull(loginId).value();
        this.encodedPassword = Objects.requireNonNull(encodedPassword).value();
        this.email = Objects.requireNonNull(email).value();
        this.role = Objects.requireNonNull(role);
        this.status = UserStatus.INACTIVE;
    }

    public static User create(UserCreateCommand command){
        Objects.requireNonNull(command);

        return new User(
                command.loginId(),
                command.password(),
                command.email(),
                UserRole.USER
        );
    }

    public void updateEmail(Email email){
        this.email = Objects.requireNonNull(email).value();
    }
}