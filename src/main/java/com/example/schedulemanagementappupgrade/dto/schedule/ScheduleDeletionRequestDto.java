package com.example.schedulemanagementappupgrade.dto.schedule;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ScheduleDeletionRequestDto {

    @NotNull(message = "Password is required.")
    private final String password;
}
