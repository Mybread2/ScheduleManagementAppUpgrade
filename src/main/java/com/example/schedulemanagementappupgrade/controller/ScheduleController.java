package com.example.schedulemanagementappupgrade.controller;

import com.example.schedulemanagementappupgrade.config.resolver.LoginUser;
import com.example.schedulemanagementappupgrade.dto.schedule.*;
import com.example.schedulemanagementappupgrade.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 내 일정 등록
    @PostMapping
    public ResponseEntity<ScheduleCreationResponseDto> createSchedule(
            @Valid @RequestBody ScheduleCreationRequestDto requestDto,
            @LoginUser Long userId)
    {
        ScheduleCreationResponseDto createScheduleResponseDto = scheduleService.createSchedule(
                userId,
                requestDto.getTitle(),
                requestDto.getContents()
        );
        return new ResponseEntity<>(createScheduleResponseDto, HttpStatus.CREATED);
    }

    // 내 일정 전체 조회
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getMySchedules(
            @LoginUser Long userId)
    {
        List<ScheduleResponseDto> foundSchedules = scheduleService.findSchedules(userId);
        return ResponseEntity.ok(foundSchedules);
    }

    // 내 일정 단건 조회
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(
            @PathVariable Long scheduleId,
            @LoginUser Long userId)
    {
        ScheduleResponseDto findScheduleWithUserNameResponseDto = scheduleService.findById(userId, scheduleId);
        return ResponseEntity.ok(findScheduleWithUserNameResponseDto);
    }

    // 내 일정 수정
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody ScheduleUpdateRequestDto requestDto,
            @LoginUser Long userId)
    {
        scheduleService.updateSchedule(userId, scheduleId ,requestDto.getTitle(), requestDto.getContents(), requestDto.getPassword());
        return ResponseEntity.ok().build();

    }

    // 내 일정 삭제
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody ScheduleDeletionRequestDto requestDto,
            @LoginUser Long userId)
    {
        scheduleService.deleteSchedule(userId, scheduleId, requestDto.getPassword());
        return ResponseEntity.noContent().build();

    }

}
