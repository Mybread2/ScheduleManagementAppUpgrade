package com.example.schedulemanagementappupgrade.dto;

import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {

    private final String userName;

    private final String password;

    private final String title;

    private final String contents;

    public CreateScheduleRequestDto(String userName, String password, String title, String contents) {
        this.userName = userName;
        this.password = password;
        this.title = title;
        this.contents = contents;
    }
}
