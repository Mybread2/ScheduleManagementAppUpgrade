package com.example.schedulemanagementappupgrade.dto.user;

import lombok.Getter;

@Getter
public class UserResponseDto {

    private final String userName;

    private final String emailAddress;

    public UserResponseDto(String userName, String emailAddress) {
        this.userName = userName;
        this.emailAddress = emailAddress;
    }
}
