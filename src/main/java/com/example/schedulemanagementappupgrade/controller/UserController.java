package com.example.schedulemanagementappupgrade.controller;

import com.example.schedulemanagementappupgrade.dto.user.*;
import com.example.schedulemanagementappupgrade.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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

    private Long getLoginUserId(HttpServletRequest request) {
        return (Long) request.getSession(false).getAttribute("userId");
    }

    // 회원가입
    @PostMapping
    public ResponseEntity<CreateUserResponseDto> createUser(
            @Valid @RequestBody CreateUserRequestDto requestDto) {
        CreateUserResponseDto createUserResponseDto = userService.createUser(
                requestDto.getUserName(),
                requestDto.getEmailAddress(),
                requestDto.getPassword()
        );

        return new ResponseEntity<>(createUserResponseDto, HttpStatus.CREATED);
    }

    // 내 정보 조회
    @GetMapping("/me")
    public ResponseEntity<FindUserResponseDto> getMyInfo(HttpServletRequest request) {

        Long userId = getLoginUserId(request);

        FindUserResponseDto responseDto = userService.findById(userId);

        return ResponseEntity.ok(responseDto);
    }

    // 내 비밀번호 변경
    @PatchMapping("/me/password")
    public ResponseEntity<Void> updatePassword(
            @Valid @RequestBody UpdatePasswordRequestDto requestDto,
            HttpServletRequest request)
    {
        Long userId = getLoginUserId(request);

        userService.updatePassword(
                userId,
                requestDto.getPreviousPassword(),
                requestDto.getNewPassword());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMyAccount(
            @Valid @RequestBody DeleteUserRequestDto requestDto,
            HttpServletRequest request)
    {
        Long userId = getLoginUserId(request);
        userService.delete(userId, requestDto.getPassword());

        // 세션 무효화
        request.getSession(false).invalidate();
        return ResponseEntity.ok().build();
    }

}