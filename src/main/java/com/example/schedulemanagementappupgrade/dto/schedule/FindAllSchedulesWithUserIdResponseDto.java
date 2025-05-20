package com.example.schedulemanagementappupgrade.dto.schedule;

import lombok.Getter;

@Getter
public class FindAllSchedulesWithUserIdResponseDto {

    private final Long id;

    private final String title;

    private final String contents;

    public FindAllSchedulesWithUserIdResponseDto(Long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }
}
