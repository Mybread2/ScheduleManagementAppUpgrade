package com.example.schedulemanagementappupgrade.controller;

import com.example.schedulemanagementappupgrade.dto.schedule.*;
import com.example.schedulemanagementappupgrade.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<CreateScheduleResponseDto> createSchedule(@RequestBody CreateScheduleRequestDto requestDto) {

        CreateScheduleResponseDto createScheduleResponseDto = scheduleService.createSchedule(
                requestDto.getUserId(),
                requestDto.getTitle(),
                requestDto.getContents()
        );
        return ResponseEntity.ok(createScheduleResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<FindAllSchedulesWithUserIdResponseDto>> findAllSchedulesWithUserId(@PathVariable Long userId){
        List<FindAllSchedulesWithUserIdResponseDto> foundSchedules = scheduleService.findSchedulesByUserId(userId);

        return new ResponseEntity<>(foundSchedules, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FindScheduleWithScheduleIdResponseDto> findScheduleById(@PathVariable Long id) {

        FindScheduleWithScheduleIdResponseDto findScheduleWithUserNameResponseDto = scheduleService.findById(id);

        return new ResponseEntity<>(findScheduleWithUserNameResponseDto, HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Void> updateSchedule(
            @PathVariable Long id,
            @RequestBody UpdateScheduleRequestDto requestDto)
    {
        scheduleService.updateSchedule(id, requestDto.getTitle(), requestDto.getContents(), requestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @RequestBody DeleteScheduleRequestDto requestDto)
    {
        scheduleService.deleteSchedule(id, requestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
