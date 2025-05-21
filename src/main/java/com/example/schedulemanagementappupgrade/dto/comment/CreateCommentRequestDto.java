package com.example.schedulemanagementappupgrade.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateCommentRequestDto {

    @NotBlank(message = "댓글 내용을 입력해주세요.")
    private final String content;

    public CreateCommentRequestDto(String content) {
        this.content = content;
    }
}
