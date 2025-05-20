package com.example.schedulemanagementappupgrade.dto.auth;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private final String userName;

    private final String password;

    private final String emailAddress;

    public LoginRequestDto(String userName, String password, String emailAddress) {
        this.userName = userName;
        this.password = password;
        this.emailAddress = emailAddress;
    }
}
