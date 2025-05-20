package com.example.schedulemanagementappupgrade.dto.auth;

import lombok.Getter;

@Getter
public class LoginResponseDto {

    private final Long userId;

    private final String userName;

    public LoginResponseDto(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
