package com.example.schedulemanagementappupgrade.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdatePasswordRequestDto {

    @NotNull(message = "이전 비밀번호는 필수입니다.")
    private final String previousPassword;

    @NotBlank(message = "새 비밀번호는 필수입니다.")
    @Size(min = 8, message = "새 비밀번호는 8자 이상이어야 합니다.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*]).*$",
            message = "새 비밀번호는 특수문자, 숫자를 포함해야 합니다."
    )
    private final String newPassword;

    public UpdatePasswordRequestDto(String previousPassword, String newPassword) {
        this.previousPassword = previousPassword;
        this.newPassword = newPassword;
    }
}
