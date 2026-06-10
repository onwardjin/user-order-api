package io.github.blairjin.user_order_api.domain.user;

import io.github.blairjin.user_order_api.application.user.command.UserProfileCreateCommand;
import io.github.blairjin.user_order_api.application.user.command.UserProfileUpdateCommand;
import io.github.blairjin.user_order_api.domain.user.vo.BirthDate;
import io.github.blairjin.user_order_api.domain.user.vo.PhoneNumber;
import io.github.blairjin.user_order_api.domain.user.vo.UserName;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Consumer;

@Entity
@Getter
@Table(name = "user_profiles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private Long userId;

    @Column(nullable = false)
    private String userName;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String phoneNumber;

    private UserProfile(Long userId, UserName userName, String firstName, String lastName, BirthDate birthDate, PhoneNumber phoneNumber){
        this.userId = Objects.requireNonNull(userId);
        this.userName = Objects.requireNonNull(userName).value();

        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate != null ? birthDate.value() : null;
        this.phoneNumber = phoneNumber != null ? phoneNumber.value() : null;
    }

    public static UserProfile create(Long userId, UserProfileCreateCommand command){
        Objects.requireNonNull(command);

        return new UserProfile(
                userId,
                command.userName(),
                command.firstName(),
                command.lastName(),
                command.birthDate(),
                command.phoneNumber()
        );
    }

    public void update(UserProfileUpdateCommand command) {
        Objects.requireNonNull(command);
        updateRequireField(command.userName(),  c -> this.userName = c.value());
        updateNullableField(command.firstName(), c -> this.firstName = c, () -> this.firstName = null);
        updateNullableField(command.lastName(), c-> this.lastName = c, () -> this.lastName = null);
        updateNullableField(command.birthDate(), c-> this.birthDate = c.value(), () -> this.birthDate = null);
        updateNullableField(command.phoneNumber(), c-> this.phoneNumber = c.value(), () -> this.phoneNumber = null);
    }

    private <T> void updateRequireField(
            JsonNullable<T> source,
            Consumer<T> updater
    ){
        if(JsonNullable.undefined().equals(source)){
            return;
        }

        T value = source.orElse(null);

        if(value==null){
            throw new IllegalArgumentException();
        }

        updater.accept(value);
    }

    private <T> void updateNullableField(
            JsonNullable<T> source,
            Consumer<T> updater,
            Runnable clearer
    ){
        if(JsonNullable.undefined().equals(source)){
            return;
        }

        T value = source.orElse(null);

        if(value==null){
            clearer.run();
            return;
        }

        updater.accept(value);
    }
}