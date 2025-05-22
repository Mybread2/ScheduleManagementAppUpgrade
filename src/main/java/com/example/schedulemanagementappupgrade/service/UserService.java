package com.example.schedulemanagementappupgrade.service;

import com.example.schedulemanagementappupgrade.config.security.PasswordEncoder;
import com.example.schedulemanagementappupgrade.dto.user.UserCreationResponseDto;
import com.example.schedulemanagementappupgrade.dto.user.UserResponseDto;
import com.example.schedulemanagementappupgrade.entity.User;
import com.example.schedulemanagementappupgrade.exception.PasswordNotFoundException;
import com.example.schedulemanagementappupgrade.exception.UserNotFoundException;
import com.example.schedulemanagementappupgrade.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserCreationResponseDto createUser(String userName, String emailAddress, String password) {

        if (userRepository.existsByEmailAddress(emailAddress)) {
            throw new DataIntegrityViolationException("This email already exists.");
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(userName, emailAddress, encodedPassword);
        User savedUser = userRepository.save(user);

        return new UserCreationResponseDto(savedUser.getId(), savedUser.getUserName(), savedUser.getEmailAddress());
    }

    public UserResponseDto findById(Long id) {

        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        return new UserResponseDto(findUser.getUserName(), findUser.getEmailAddress(), findUser.getPassword());
    }

    @Transactional
    public void updatePassword(Long id, String previousPassword, String newPassword) {

        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if(passwordEncoder.matches(previousPassword, findUser.getPassword())){
            throw new PasswordNotFoundException("Password is not correct");
        }

        User updatedPassword = new User(
                findUser.getUserName(),
                findUser.getEmailAddress(),
                passwordEncoder.encode(newPassword)
        );

        userRepository.save(updatedPassword);
    }

    @Transactional
    public void deleteUser(Long id, String userName, String rawPassword) {

        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if (!findUser.getUserName().equals(userName)) {
            throw new UserNotFoundException("User Not Found");
        }

        if (passwordEncoder.matches(rawPassword, findUser.getPassword())) {
            throw new PasswordNotFoundException("Password is not correct");
        }

        userRepository.deleteById(id);
    }
}
