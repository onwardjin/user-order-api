package com.example.userorder.domain.user;

import com.example.userorder.domain.common.BaseTimeEntity;
import com.example.userorder.domain.user.vo.BirthDate;
import com.example.userorder.domain.user.vo.Email;
import com.example.userorder.domain.user.vo.UserName;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user_profiles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private UserName name;

    @Embedded
    private BirthDate birthDate;

    @Embedded
    private Email email;

    private UserProfile(UserName name, BirthDate birthDate, Email email) {
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
    }

    static UserProfile createUserProfile(UserName name, BirthDate birthDate, Email email) {
        return new UserProfile(name, birthDate, email);
    }

    public void updateProfile(UserName name, BirthDate birthDate, Email email) {
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
    }
}
