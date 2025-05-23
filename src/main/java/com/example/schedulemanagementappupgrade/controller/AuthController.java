package com.example.schedulemanagementappupgrade.controller;

import com.example.schedulemanagementappupgrade.dto.auth.LoginRequestDto;
import com.example.schedulemanagementappupgrade.dto.auth.LoginResponseDto;
import com.example.schedulemanagementappupgrade.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService; // LoginService 주입

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto loginRequestDto,
            HttpServletRequest request) {

        LoginResponseDto loginResponse = authService.login(loginRequestDto, request);
        return ResponseEntity.ok(loginResponse);
    }
}