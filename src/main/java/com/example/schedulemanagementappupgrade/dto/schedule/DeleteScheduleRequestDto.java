package com.example.schedulemanagementappupgrade.dto.schedule;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DeleteScheduleRequestDto {

    @NotNull(message = "비밀번호는 필수입니다.")
    private final String password;

    public DeleteScheduleRequestDto(String password) {
        this.password = password;
    }
}
