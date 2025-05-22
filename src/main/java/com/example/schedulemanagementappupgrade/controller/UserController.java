package com.example.schedulemanagementappupgrade.controller;

import com.example.schedulemanagementappupgrade.config.annotation.LoginUser;
import com.example.schedulemanagementappupgrade.dto.user.*;
import com.example.schedulemanagementappupgrade.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping
    public ResponseEntity<UserCreationResponseDto> createUser(
            @Valid @RequestBody UserCreationRequestDto requestDto) {
        UserCreationResponseDto createUserResponseDto = userService.createUser(
                requestDto.getUserName(),
                requestDto.getEmailAddress(),
                requestDto.getPassword()
        );
        return new ResponseEntity<>(createUserResponseDto, HttpStatus.CREATED);
    }

    // 내 정보 조회
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyInfo(
            @LoginUser Long userId)
    {
        UserResponseDto responseDto = userService.findById(userId);
        return ResponseEntity.ok(responseDto);
    }

    // 내 비밀번호 변경
    @PatchMapping("/me/password")
    public ResponseEntity<Void> updatePassword(
            @Valid @RequestBody PasswordUpdateRequestDto requestDto,
            @LoginUser Long userId)
    {
        userService.updatePassword(
                userId,
                requestDto.getPreviousPassword(),
                requestDto.getNewPassword());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMyAccount(
            @Valid @RequestBody UserDeletionRequestDto requestDto,
            @LoginUser Long userId)
    {
        userService.deleteUser(
                userId,
                requestDto.getUserName(),
                requestDto.getPassword());

        return ResponseEntity.noContent().build();
    }

}