package com.example.schedulemanagementappupgrade.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class LoginRequestDto {

    @NotNull(message = "유저명은 필수입니다.")
    private final String userName;

    @NotNull(message = "비밀번호는 필수입니다.")
    private final String password;

    @NotNull(message = "이메일은 필수입니다.")
    private final String emailAddress;
}
