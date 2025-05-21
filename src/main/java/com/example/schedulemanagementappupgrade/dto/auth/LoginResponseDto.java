package com.example.schedulemanagementappupgrade.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class LoginResponseDto {

    private final Long userId;

    private final String userName;
}
