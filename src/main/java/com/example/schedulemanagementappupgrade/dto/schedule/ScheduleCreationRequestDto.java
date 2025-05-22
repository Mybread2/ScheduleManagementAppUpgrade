package com.example.schedulemanagementappupgrade.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ScheduleCreationRequestDto {
    @NotBlank(message = "Title is required.")
    @Size(max = 50, message = "Title must be less than 90 characters")
    private final String title;

    @NotBlank(message = "Content is required.")
    @Size(max = 500, message = "Content must be less than 500 characters")
    private final String contents;
}
