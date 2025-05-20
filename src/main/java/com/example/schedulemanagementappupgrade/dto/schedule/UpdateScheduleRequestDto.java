package com.example.schedulemanagementappupgrade.dto.schedule;

import lombok.Getter;

@Getter
public class UpdateScheduleRequestDto {
    private final String title;

    private final String contents;

    private final String password;

    public UpdateScheduleRequestDto(String title, String contents, String password) {
        this.title = title;
        this.contents = contents;
        this.password = password;
    }
}
