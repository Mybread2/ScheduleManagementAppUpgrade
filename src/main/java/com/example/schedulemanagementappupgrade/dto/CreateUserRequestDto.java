package com.example.schedulemanagementappupgrade.dto;

import lombok.Getter;

@Getter
public class CreateUserRequestDto {

    private final String userName;
    private final String emailAddress;
    private final String password;

    public CreateUserRequestDto(String userName, String emailAddress, String password) {
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.password = password;
    }
}
