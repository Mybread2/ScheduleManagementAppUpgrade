package com.example.schedulemanagementappupgrade.controller;

import com.example.schedulemanagementappupgrade.dto.auth.LoginRequestDto;
import com.example.schedulemanagementappupgrade.dto.auth.LoginResponseDto;
import com.example.schedulemanagementappupgrade.entity.User;
import com.example.schedulemanagementappupgrade.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto requestDto,
            HttpServletRequest request
            ) {
        User user = authService.authenticate(requestDto.getUserName(), requestDto.getPassword(), requestDto.getEmailAddress());

        HttpSession session = request.getSession(true);
        session.setAttribute("userId", user.getId());

        LoginResponseDto responseDto = new LoginResponseDto(user.getId(), user.getUserName());
        return ResponseEntity.ok(responseDto);
    }
}
