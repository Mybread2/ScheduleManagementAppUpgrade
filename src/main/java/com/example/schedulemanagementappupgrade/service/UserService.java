package com.example.schedulemanagementappupgrade.service;

import com.example.schedulemanagementappupgrade.dto.UserResponseDto;
import com.example.schedulemanagementappupgrade.entity.User;
import com.example.schedulemanagementappupgrade.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto createUser(String userName, String password, String emailAddress) {

        User user = new User(userName, password, emailAddress);

        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser.getId(), savedUser.getUserName(), savedUser.getEmailAddress());
    }
}
