package com.example.schedulemanagementappupgrade.dto.comment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class DeletionCommentRequestDto {

    @NotNull(message = "비밀번호는 필수입니다.")
    private final String password;
}
