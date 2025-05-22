package com.example.schedulemanagementappupgrade.dto.user;

import lombok.Getter;

@Getter
public class UserResponseDto {

    private final String userName;

    private final String emailAddress;

    private final String password;

    public UserResponseDto(String userName, String emailAddress, String password) {
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.password = password;
    }
}
