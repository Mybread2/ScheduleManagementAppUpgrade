package com.example.schedulemanagementappupgrade.controller;

import com.example.schedulemanagementappupgrade.dto.user.*;
import com.example.schedulemanagementappupgrade.entity.User;
import com.example.schedulemanagementappupgrade.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
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
            @AuthenticationPrincipal UserDetails userDetails)
    {
        User currentUser = userService.findByEmailAddressOrThrow(userDetails.getUsername());
        UserResponseDto responseDto = userService.findUserDtoById(currentUser.getId());
        return ResponseEntity.ok(responseDto);
    }

    // 내 비밀번호 변경
    @PatchMapping("/me/password")
    public ResponseEntity<Void> updatePassword(
            @Valid @RequestBody PasswordUpdateRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails)
    {
        User currentUser = userService.findByEmailAddressOrThrow(userDetails.getUsername());
        userService.updatePassword(
                currentUser.getId(),
                requestDto.getPreviousPassword(),
                requestDto.getNewPassword());

        return ResponseEntity.ok().build();
    }

    // 회원 탈퇴
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMyAccount(
            @Valid @RequestBody UserDeletionRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails)
    {
        User currentUser = userService.findByEmailAddressOrThrow(userDetails.getUsername());
        userService.deleteUser(
                currentUser.getId(),
                requestDto.getUserName(),
                requestDto.getPassword());

        return ResponseEntity.noContent().build();
    }
}