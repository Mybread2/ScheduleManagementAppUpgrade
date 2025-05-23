package com.example.schedulemanagementappupgrade.service;

import com.example.schedulemanagementappupgrade.dto.user.UserCreationResponseDto;
import com.example.schedulemanagementappupgrade.dto.user.UserResponseDto;
import com.example.schedulemanagementappupgrade.entity.User;
import com.example.schedulemanagementappupgrade.exception.PasswordNotCorrectException;
import com.example.schedulemanagementappupgrade.exception.SameEmailExistException;
import com.example.schedulemanagementappupgrade.exception.UserNameNotCorrectException;
import com.example.schedulemanagementappupgrade.exception.UserNotFoundException;
import com.example.schedulemanagementappupgrade.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private User findUserByIdOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found with id: " + userId));
    }

    private void verifyUserPassword(User user, String rawPassword) {
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new PasswordNotCorrectException("Password is required.");
        }
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new PasswordNotCorrectException("Password is not correct");
        }
    }

    private void verifyUserName(User user, String userNameToVerify) {
        if (userNameToVerify == null || userNameToVerify.isBlank()) {
            throw new UserNameNotCorrectException("Username is required for verification.");
        }
        if (!user.getUserName().equals(userNameToVerify)) {
            throw new UserNameNotCorrectException("UserName is not correct");
        }
    }

    private void checkEmailExistence(String emailAddress) {
        if (userRepository.existsByEmailAddress(emailAddress)) {
            throw new SameEmailExistException("This email already exists.");
        }
    }

    @Transactional
    public UserCreationResponseDto createUser(String userName, String emailAddress, String password) {
        checkEmailExistence(emailAddress);

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(userName, emailAddress, encodedPassword); // User 생성자에 role 파라미터 추가 가정
        User savedUser = userRepository.save(user);

        return new UserCreationResponseDto(savedUser.getId(), savedUser.getUserName(), savedUser.getEmailAddress());
    }

    public User findByEmailAddressOrThrow(String emailAddress) {
        return userRepository.findByEmailAddress(emailAddress)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + emailAddress));
    }

    public UserResponseDto findUserDtoById(Long id) {
        User findUser = findUserByIdOrThrow(id);
        return new UserResponseDto(findUser.getUserName(), findUser.getEmailAddress());
    }

    @Transactional
    public void updatePassword(Long id, String previousPassword, String newPassword) {
        User findUser = findUserByIdOrThrow(id);
        verifyUserPassword(findUser, previousPassword);

        if (newPassword == null || newPassword.isBlank()) { // 새 비밀번호 유효성 검사 추가
            throw new IllegalArgumentException("New password cannot be empty.");
        }

        findUser.updatePassword(passwordEncoder.encode(newPassword));
    }

    @Transactional
    public void deleteUser(Long id, String userName, String rawPassword) {
        User findUser = findUserByIdOrThrow(id);
        verifyUserName(findUser, userName); // 사용자 이름 검증
        verifyUserPassword(findUser, rawPassword); // 비밀번호 검증

        userRepository.delete(findUser); // ID로 삭제하는 것보다 조회한 엔티티 객체로 삭제하는 것을 고려
    }
}
