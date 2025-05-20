package com.example.schedulemanagementappupgrade.dto.user;

import lombok.Getter;

@Getter
public class FindUserResponseDto {

    private final String userName;

    private final String emailAddress;

    private final String password;

    public FindUserResponseDto(String userName, String emailAddress, String password) {
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.password = password;
    }
}
