package com.example.schedulemanagementappupgrade.controller;

import com.example.schedulemanagementappupgrade.dto.schedule.*;
import com.example.schedulemanagementappupgrade.entity.User;
import com.example.schedulemanagementappupgrade.service.ScheduleService;
import com.example.schedulemanagementappupgrade.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final UserService userService;

    // 내 일정 등록
    @PostMapping
    public ResponseEntity<ScheduleCreationResponseDto> createSchedule(
            @Valid @RequestBody ScheduleCreationRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails)
    {
        User currentUser = userService.findByEmailAddressOrThrow(userDetails.getUsername());
        ScheduleCreationResponseDto createScheduleResponseDto = scheduleService.createSchedule(
                currentUser.getId(), // 실제 사용자 ID 전달
                requestDto.getTitle(),
                requestDto.getContents()
        );
        return new ResponseEntity<>(createScheduleResponseDto, HttpStatus.CREATED);
    }

    // 내 일정 전체 조회
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getMySchedules(
            @AuthenticationPrincipal UserDetails userDetails)
    {
        User currentUser = userService.findByEmailAddressOrThrow(userDetails.getUsername());
        List<ScheduleResponseDto> foundSchedules = scheduleService.findSchedules(currentUser.getId());
        return ResponseEntity.ok(foundSchedules);
    }

    // 내 일정 단건 조회
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(
            @PathVariable Long scheduleId,
            @AuthenticationPrincipal UserDetails userDetails)
    {
        User currentUser = userService.findByEmailAddressOrThrow(userDetails.getUsername());
        ScheduleResponseDto findScheduleResponseDto = scheduleService.findById(currentUser.getId(), scheduleId);
        return ResponseEntity.ok(findScheduleResponseDto);
    }

    // 내 일정 수정
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody ScheduleUpdateRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails)
    {
        User currentUser = userService.findByEmailAddressOrThrow(userDetails.getUsername());
        scheduleService.updateSchedule(
                currentUser.getId(),
                scheduleId ,
                requestDto.getTitle(),
                requestDto.getContents(),
                requestDto.getPassword());

        return ResponseEntity.ok().build();
    }

    // 내 일정 삭제
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody ScheduleDeletionRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails)
    {
        User currentUser = userService.findByEmailAddressOrThrow(userDetails.getUsername());
        scheduleService.deleteSchedule(
                currentUser.getId(),
                scheduleId,
                requestDto.getPassword());

        return ResponseEntity.noContent().build();
    }

}
