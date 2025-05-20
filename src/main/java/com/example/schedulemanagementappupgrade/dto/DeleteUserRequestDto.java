package com.example.schedulemanagementappupgrade.dto;

import lombok.Getter;

@Getter
public class DeleteUserRequestDto {

    private final String userName;

    private final String password;

    public DeleteUserRequestDto(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
