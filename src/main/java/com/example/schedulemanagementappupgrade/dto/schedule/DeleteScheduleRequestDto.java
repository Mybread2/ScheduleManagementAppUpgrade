package com.example.schedulemanagementappupgrade.dto.schedule;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class DeleteScheduleRequestDto {

    @NotNull(message = "비밀번호는 필수입니다.")
    private final String password;
}
