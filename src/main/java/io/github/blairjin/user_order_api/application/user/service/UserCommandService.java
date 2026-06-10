package io.github.blairjin.user_order_api.application.user.service;

import io.github.blairjin.user_order_api.application.user.command.UserCreateCommand;
import io.github.blairjin.user_order_api.application.user.command.UserProfileCreateCommand;
import io.github.blairjin.user_order_api.application.user.command.UserProfileUpdateCommand;
import io.github.blairjin.user_order_api.application.user.reader.UserReader;
import io.github.blairjin.user_order_api.domain.user.User;
import io.github.blairjin.user_order_api.domain.user.UserProfile;
import io.github.blairjin.user_order_api.domain.user.vo.Email;
import io.github.blairjin.user_order_api.repository.user.UserProfileRepository;
import io.github.blairjin.user_order_api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserReader userReader;

    @Transactional
    public void create(UserCreateCommand userCreateCommand, UserProfileCreateCommand profileCommand){
        User user = User.create(userCreateCommand);
        User savedUser = userRepository.save(user);

        UserProfile profile = UserProfile.create(savedUser.getId(), profileCommand);
        userProfileRepository.save(profile);
    }

    @Transactional
    public void update(Long userId, Email email){
        User user = userReader.getUserById(userId);
        user.updateEmail(email);
    }

    @Transactional
    public void updateProfile(Long userId, UserProfileUpdateCommand command){
        UserProfile profile = userReader.getUserProfileByUserId(userId);
        profile.update(command);
    }

    @Transactional
    public void delete(Long userId){
        User user = userReader.getUserById(userId);
        UserProfile profile = userReader.getUserProfileByUserId(userId);
        // TODO: Address entity

        userRepository.delete(user);
        userProfileRepository.delete(profile);
    }
}