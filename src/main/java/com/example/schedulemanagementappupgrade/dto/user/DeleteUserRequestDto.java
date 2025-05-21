package com.example.schedulemanagementappupgrade.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DeleteUserRequestDto {

    @NotNull(message = "유저명은 필수입니다.")
    private final String userName;

    @NotNull(message = "비밀번호는 필수입니다.")
    private final String password;

    public DeleteUserRequestDto(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
