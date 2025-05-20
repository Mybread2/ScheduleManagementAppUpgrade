package com.example.schedulemanagementappupgrade.dto;

import lombok.Getter;

@Getter
public class CreateScheduleResponseDto {

    private final Long id;

    private final String title;

    private final String contents;

    public CreateScheduleResponseDto(Long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }
}
