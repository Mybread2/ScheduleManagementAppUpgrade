package com.example.schedulemanagementappupgrade.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdateRequestDto {

    @NotBlank(message = "Content is required")
    private String content;

    @NotBlank(message = "Password is required")
    private String password;
}
