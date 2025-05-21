package com.example.schedulemanagementappupgrade.dto.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateScheduleResponseDto {
    private final Long id;
    private final String title;
    private final String contents;
}
