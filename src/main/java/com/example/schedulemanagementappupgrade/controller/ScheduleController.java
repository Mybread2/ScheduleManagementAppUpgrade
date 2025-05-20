package com.example.schedulemanagementappupgrade.controller;

import com.example.schedulemanagementappupgrade.dto.schedule.CreateScheduleRequestDto;
import com.example.schedulemanagementappupgrade.dto.schedule.CreateScheduleResponseDto;
import com.example.schedulemanagementappupgrade.dto.schedule.FindAllSchedulesWithUserIdResponseDto;
import com.example.schedulemanagementappupgrade.dto.schedule.FindScheduleWithScheduleIdResponseDto;
import com.example.schedulemanagementappupgrade.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/users/{userId}/schedules")
    public ResponseEntity<CreateScheduleResponseDto> createSchedule(@RequestBody CreateScheduleRequestDto requestDto) {

        CreateScheduleResponseDto createScheduleResponseDto = scheduleService.createSchedule(
                requestDto.getUserId(),
                requestDto.getTitle(),
                requestDto.getContents()
        );
        return ResponseEntity.ok(createScheduleResponseDto);
    }

    @GetMapping("/users/{userId}/schedules")
    public ResponseEntity<List<FindAllSchedulesWithUserIdResponseDto>> findAllSchedulesWithUserId(@PathVariable Long userId){
        List<FindAllSchedulesWithUserIdResponseDto> foundSchedules = scheduleService.findSchedulesByUserId(userId);

        return new ResponseEntity<>(foundSchedules, HttpStatus.OK);
    }

    @GetMapping("/schedules/{id}")
    public ResponseEntity<FindScheduleWithScheduleIdResponseDto> findScheduleById(@PathVariable Long id) {

        FindScheduleWithScheduleIdResponseDto findScheduleWithUserNameResponseDto = scheduleService.findById(id);

        return new ResponseEntity<>(findScheduleWithUserNameResponseDto, HttpStatus.OK);
    }

}
