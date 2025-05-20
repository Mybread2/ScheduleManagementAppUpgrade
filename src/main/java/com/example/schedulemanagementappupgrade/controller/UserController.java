package com.example.schedulemanagementappupgrade.controller;

import com.example.schedulemanagementappupgrade.dto.*;
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
    public ResponseEntity<CreateUserResponseDto> createUser(@RequestBody CreateUserRequestDto requestDto) {

        CreateUserResponseDto createUserResponseDto = userService.createUser(
                requestDto.getUserName(),
                requestDto.getEmailAddress(),
                requestDto.getPassword()
        );

        return new ResponseEntity<>(createUserResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FindUserResponseDto> findUserById(@PathVariable Long id) {

        FindUserResponseDto findUserResponseDto = userService.findById(id);

        return new ResponseEntity<>(findUserResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(
            @PathVariable Long id,
            @RequestBody UpdatePasswordRequestDto requestDto)
    {
        userService.updatePassword(id, requestDto.getPreviousPassword(), requestDto.getNewPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @RequestBody DeleteUserRequestDto requestDto)
    {
        userService.delete(id, requestDto.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
