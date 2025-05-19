package com.example.schedulemanagementappupgrade.controller;

import com.example.schedulemanagementappupgrade.dto.UserRequestDto;
import com.example.schedulemanagementappupgrade.dto.UserResponseDto;
import com.example.schedulemanagementappupgrade.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto requestDto) {

        UserResponseDto userResponseDto = userService.createUser(
                requestDto.getUserName(),
                requestDto.getPassword(),
                requestDto.getEmailAddress()
        );

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }
}
