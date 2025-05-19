package com.example.schedulemanagementappupgrade.dto;

import lombok.Getter;

@Getter
public class UserRequestDto {

    private final String userName;
    private final String emailAddress;
    private final String password;

    public UserRequestDto(String userName, String emailAddress, String password) {
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.password = password;
    }
}
