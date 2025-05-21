package com.example.schedulemanagementappupgrade.dto.comment;

import lombok.Getter;

@Getter
public class CreateCommentResponseDto {
    private final Long id;
    private final String userName;
    private final String content;

    public CreateCommentResponseDto(Long id, String userName, String content) {
        this.id = id;
        this.userName = userName;
        this.content = content;
    }
}
