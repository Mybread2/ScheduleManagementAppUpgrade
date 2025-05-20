package com.example.schedulemanagementappupgrade.service;

import com.example.schedulemanagementappupgrade.dto.FindUserResponseDto;
import com.example.schedulemanagementappupgrade.dto.UserResponseDto;
import com.example.schedulemanagementappupgrade.entity.User;
import com.example.schedulemanagementappupgrade.exception.PasswordNotFoundException;
import com.example.schedulemanagementappupgrade.exception.UserNotFoundException;
import com.example.schedulemanagementappupgrade.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto createUser(String userName, String emailAddress, String password) {

        User user = new User(userName, emailAddress, password);

        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser.getId(), savedUser.getUserName(), savedUser.getEmailAddress());
    }

    public FindUserResponseDto findById(Long id) {

        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        return new FindUserResponseDto(findUser.getUserName(), findUser.getEmailAddress(), findUser.getPassword());
    }

    @Transactional
    public void updatePassword(Long id, String previousPassword, String newPassword) {

        User findUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if (!findUser.getPassword().equals(previousPassword)) {
            throw new PasswordNotFoundException("Password is not correct");
        }

        findUser.updatePassword(newPassword);
    }

    public void delete(Long id, String password) {

        User findUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if (!findUser.getPassword().equals(password)) {
            throw new UserNotFoundException("Password is not correct");
        }

        userRepository.deleteById(id);
    }
}
