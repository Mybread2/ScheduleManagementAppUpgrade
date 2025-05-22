package com.example.schedulemanagementappupgrade.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class LoginRequestDto {

    @NotNull(message = "UserName is required.")
    private final String userName;

    @NotNull(message = "Password is required.")
    private final String password;

    @NotNull(message = "EmailAddress is required.")
    private final String emailAddress;
}
