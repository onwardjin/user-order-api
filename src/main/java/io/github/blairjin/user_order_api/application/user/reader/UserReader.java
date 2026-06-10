package io.github.blairjin.user_order_api.application.user.reader;

import io.github.blairjin.user_order_api.domain.user.User;
import io.github.blairjin.user_order_api.domain.user.UserProfile;
import io.github.blairjin.user_order_api.domain.user.vo.LoginId;
import io.github.blairjin.user_order_api.domain.user.vo.UserName;
import io.github.blairjin.user_order_api.dto.user.UserNameResult;
import io.github.blairjin.user_order_api.exception.CONFLICT.DuplicateLoginIdException;
import io.github.blairjin.user_order_api.exception.NOT_FOUND.UserNotFoundException;
import io.github.blairjin.user_order_api.exception.NOT_FOUND.UserProfileNotFoundException;
import io.github.blairjin.user_order_api.repository.user.UserProfileRepository;
import io.github.blairjin.user_order_api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserReader {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public void validateDuplicateLoginId(LoginId loginId){
        if(userRepository.existsByLoginId(loginId.value())){
            throw new DuplicateLoginIdException();
        }
    }

    public User getUserByLoginId(LoginId loginId){
        return userRepository.findByLoginId(loginId.value())
                .orElseThrow(UserNotFoundException::new);
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public Map<Long, String> getUserNameByIds(List<Long> userIds){
        List<UserNameResult> list = userProfileRepository.findUserNamesByUserIds(userIds);
        return list.stream().collect(Collectors.toMap(UserNameResult::userId, UserNameResult::userName));
    }

    public UserName getUserNameById(Long userId){
        String result = userProfileRepository.findUserNameByUserId(userId)
                .orElseThrow(UserNotFoundException::new);

        return UserName.of(result);
    }

    public UserProfile getUserProfileByUserId(Long userId){
        return userProfileRepository.findByUserId(userId)
                .orElseThrow(UserProfileNotFoundException::new);
    }
}