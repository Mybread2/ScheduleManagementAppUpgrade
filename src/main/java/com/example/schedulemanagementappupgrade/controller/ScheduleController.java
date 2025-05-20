package com.example.schedulemanagementappupgrade.controller;

import com.example.schedulemanagementappupgrade.dto.CreateScheduleRequestDto;
import com.example.schedulemanagementappupgrade.dto.CreateScheduleResponseDto;
import com.example.schedulemanagementappupgrade.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<CreateScheduleResponseDto> createSchedule(@RequestBody CreateScheduleRequestDto requestDto) {

        CreateScheduleResponseDto createScheduleResponseDto = scheduleService.createSchedule(
                requestDto.getUserName(),
                requestDto.getPassword(),
                requestDto.getTitle(),
                requestDto.getContents()
        );
        return ResponseEntity.ok(createScheduleResponseDto);
    }
}
