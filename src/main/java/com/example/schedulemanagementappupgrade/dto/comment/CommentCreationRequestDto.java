package com.example.schedulemanagementappupgrade.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CommentCreationRequestDto {

    @NotBlank(message = "Content is required.")
    private final String content;
}
