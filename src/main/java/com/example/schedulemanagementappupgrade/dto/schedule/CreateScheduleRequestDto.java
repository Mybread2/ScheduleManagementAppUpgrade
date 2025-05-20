package com.example.schedulemanagementappupgrade.dto.schedule;

import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {

    private final Long userId;

    private final String title;

    private final String contents;

    public CreateScheduleRequestDto(Long userId, String title, String contents) {
        this.userId = userId;
        this.title = title;
        this.contents = contents;
    }
}
