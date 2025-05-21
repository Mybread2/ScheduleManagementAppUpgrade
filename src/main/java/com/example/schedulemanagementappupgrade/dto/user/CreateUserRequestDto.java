package com.example.schedulemanagementappupgrade.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserRequestDto {

    @NotBlank
    @Size(max = 4, message = "유저명은 4글자 이하여야 합니다.")
    private final String userName;

    @NotBlank
    @Email(message = "이메일 주소 형식이여야 합니다.")
    private final String emailAddress;

    @NotBlank
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이여야 합니다.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*]).*$",
            message = "비밀번호는 특수문자, 숫자를 포합해야합니다."
    )
    private final String password;

    public CreateUserRequestDto(String userName, String emailAddress, String password) {
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.password = password;
    }
}
