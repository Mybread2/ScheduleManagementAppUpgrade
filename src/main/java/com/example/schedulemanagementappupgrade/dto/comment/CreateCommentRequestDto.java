package com.example.schedulemanagementappupgrade.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateCommentRequestDto {

    @NotBlank(message = "댓글 내용을 입력해주세요.")
    private final String content;
}
