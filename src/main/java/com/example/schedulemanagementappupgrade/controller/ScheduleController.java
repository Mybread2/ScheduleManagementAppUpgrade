package com.example.schedulemanagementappupgrade.controller;

import com.example.schedulemanagementappupgrade.dto.schedule.*;
import com.example.schedulemanagementappupgrade.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 로그인 유무 판단하는 로직 (세션에 로그인한 userId가 있는지 검증)
    private Long getLoginUserId(HttpServletRequest request) {
       return (Long) request.getSession(false).getAttribute("userId");
    }

    // 내 일정 등록
    @PostMapping
    public ResponseEntity<CreateScheduleResponseDto> createSchedule(
            @Valid @RequestBody CreateScheduleRequestDto requestDto,
            HttpServletRequest request) {

        Long userId = getLoginUserId(request);

        CreateScheduleResponseDto createScheduleResponseDto = scheduleService.createSchedule(
                userId,
                requestDto.getTitle(),
                requestDto.getContents()
        );
        return ResponseEntity.ok(createScheduleResponseDto);
    }

    // 내 일정 전체 조회
    @GetMapping
    public ResponseEntity<List<FindAllSchedulesWithUserIdResponseDto>> getMySchedules(
            HttpServletRequest request){

        Long userId = getLoginUserId(request);
        List<FindAllSchedulesWithUserIdResponseDto> foundSchedules = scheduleService.findSchedules(userId);

        return ResponseEntity.ok(foundSchedules);
    }

    // 내 일정 단건 조회
    @GetMapping("/{scheduleId}")
    public ResponseEntity<FindScheduleWithScheduleIdResponseDto> findScheduleById(
            @PathVariable Long scheduleId,
            HttpServletRequest request) {

        Long userId = getLoginUserId(request);
        FindScheduleWithScheduleIdResponseDto findScheduleWithUserNameResponseDto = scheduleService.findById(userId, scheduleId);

        return ResponseEntity.ok(findScheduleWithUserNameResponseDto);
    }

    // 내 일정 수정
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody UpdateScheduleRequestDto requestDto,
            HttpServletRequest request)
    {
        Long userId = getLoginUserId(request);
        scheduleService.updateSchedule(userId, scheduleId ,requestDto.getTitle(), requestDto.getContents(), requestDto.getPassword());
        return ResponseEntity.ok().build();

    }

    // 내 일정 삭제
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody DeleteScheduleRequestDto requestDto,
            HttpServletRequest request)
    {
        Long userId = getLoginUserId(request);
        scheduleService.deleteSchedule(userId, scheduleId, requestDto.getPassword());
        return ResponseEntity.ok().build();

    }

}
