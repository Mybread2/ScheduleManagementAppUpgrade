package com.example.schedulemanagementappupgrade.controller;

import com.example.schedulemanagementappupgrade.dto.FindUserResponseDto;
import com.example.schedulemanagementappupgrade.dto.UserRequestDto;
import com.example.schedulemanagementappupgrade.dto.UserResponseDto;
import com.example.schedulemanagementappupgrade.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto requestDto) {

        UserResponseDto userResponseDto = userService.createUser(
                requestDto.getUserName(),
                requestDto.getEmailAddress(),
                requestDto.getPassword()
        );

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FindUserResponseDto> findUserById(@PathVariable Long id) {

        FindUserResponseDto findUserResponseDto = userService.findById(id);

        return new ResponseEntity<>(findUserResponseDto, HttpStatus.OK);
    }
}
