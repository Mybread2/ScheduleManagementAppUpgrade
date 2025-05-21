package com.example.schedulemanagementappupgrade.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CommentResponseDto {

    private final Long id;

    private final String content;
}
