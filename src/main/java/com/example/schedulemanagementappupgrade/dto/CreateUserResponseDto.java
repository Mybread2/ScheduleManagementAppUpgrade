package com.example.schedulemanagementappupgrade.dto;

import lombok.Getter;

@Getter
public class CreateUserResponseDto {

    private final Long id;

    private final String userName;

    private final String emailAddress;

    public CreateUserResponseDto(Long id, String userName, String emailAddress) {
        this.id = id;
        this.userName = userName;
        this.emailAddress = emailAddress;
    }
}
