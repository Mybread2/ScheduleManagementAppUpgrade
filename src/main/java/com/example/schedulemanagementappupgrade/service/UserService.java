package com.example.schedulemanagementappupgrade.service;

import com.example.schedulemanagementappupgrade.config.security.PasswordEncoder;
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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }

    private void verifyUserPassword(User user, String rawPassword) {
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new PasswordNotCorrectException("Password is not correct");
        }
    }

    private void verifyUserName(User user, String userName) {
        if (!user.getUserName().equals(userName)) {
            throw new UserNameNotCorrectException("UserName is not correct");
        }
    }

    private void verifyEmailAddress(String emailAddress) {
        if (userRepository.existsByEmailAddress(emailAddress)) {
            throw new SameEmailExistException("This email already exists.");
        }
    }

    @Transactional
    public UserCreationResponseDto createUser(String userName, String emailAddress, String password) {
        verifyEmailAddress(emailAddress);

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(userName, emailAddress, encodedPassword);
        User savedUser = userRepository.save(user);

        return new UserCreationResponseDto(savedUser.getId(), savedUser.getUserName(), savedUser.getEmailAddress());
    }

    @Transactional
    public UserResponseDto findById(Long id) {
        User findUser = findUserById(id);

        return new UserResponseDto(findUser.getUserName(), findUser.getEmailAddress());
    }

    @Transactional
    public void updatePassword(Long id, String previousPassword, String newPassword) {
        User findUser = findUserById(id);
        verifyUserPassword(findUser, previousPassword);

        findUser.updatePassword(passwordEncoder.encode(newPassword));
    }

    @Transactional
    public void deleteUser(Long id, String userName, String rawPassword) {
        User findUser = findUserById(id);
        verifyUserName(findUser, userName);
        verifyUserPassword(findUser, rawPassword);

        userRepository.deleteById(id);
    }
}
