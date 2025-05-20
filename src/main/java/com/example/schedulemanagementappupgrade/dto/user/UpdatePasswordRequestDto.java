package com.example.schedulemanagementappupgrade.dto.user;

import lombok.Getter;

@Getter
public class UpdatePasswordRequestDto {

    private final String previousPassword;

    private final String newPassword;

    public UpdatePasswordRequestDto(String previousPassword, String newPassword) {
        this.previousPassword = previousPassword;
        this.newPassword = newPassword;
    }
}
