package com.example.schedulemanagementappupgrade.dto.user;

import lombok.Getter;

@Getter
public class UserCreationResponseDto {

    private final Long id;

    private final String userName;

    private final String emailAddress;

    public UserCreationResponseDto(Long id, String userName, String emailAddress) {
        this.id = id;
        this.userName = userName;
        this.emailAddress = emailAddress;
    }
}
