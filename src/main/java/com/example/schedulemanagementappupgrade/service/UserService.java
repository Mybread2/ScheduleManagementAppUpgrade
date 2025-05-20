package com.example.schedulemanagementappupgrade.service;

import com.example.schedulemanagementappupgrade.dto.FindUserResponseDto;
import com.example.schedulemanagementappupgrade.dto.UserResponseDto;
import com.example.schedulemanagementappupgrade.entity.User;
import com.example.schedulemanagementappupgrade.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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

        Optional<User> optionalUser = userRepository.findById(id);

        // NPE 방지
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }

        User findUser = optionalUser.get();

        return new FindUserResponseDto(findUser.getUserName(), findUser.getEmailAddress(), findUser.getPassword());
    }
}
