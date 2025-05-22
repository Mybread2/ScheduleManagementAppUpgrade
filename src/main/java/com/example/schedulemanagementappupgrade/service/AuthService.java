package com.example.schedulemanagementappupgrade.service;

import com.example.schedulemanagementappupgrade.config.PasswordEncoder;
import com.example.schedulemanagementappupgrade.entity.User;
import com.example.schedulemanagementappupgrade.exception.EmailAddressNotFoundException;
import com.example.schedulemanagementappupgrade.exception.UserNotFoundException;
import com.example.schedulemanagementappupgrade.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User authenticate(String userName, String password, String emailAddress) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if(passwordEncoder.matches(password, user.getPassword())
        ) throw new UserNotFoundException("Password is not correct");

        if(!user.getEmailAddress().equals(emailAddress)) throw new EmailAddressNotFoundException("Email Address is not correct");

        return user;
    }
}
