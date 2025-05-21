package com.example.schedulemanagementappupgrade.service;

import com.example.schedulemanagementappupgrade.dto.schedule.CreateScheduleResponseDto;
import com.example.schedulemanagementappupgrade.dto.schedule.FindAllSchedulesWithUserIdResponseDto;
import com.example.schedulemanagementappupgrade.dto.schedule.FindScheduleWithScheduleIdResponseDto;
import com.example.schedulemanagementappupgrade.entity.Schedule;
import com.example.schedulemanagementappupgrade.entity.User;
import com.example.schedulemanagementappupgrade.exception.PasswordNotFoundException;
import com.example.schedulemanagementappupgrade.exception.ScheduleNotFoundException;
import com.example.schedulemanagementappupgrade.exception.UserNotFoundException;
import com.example.schedulemanagementappupgrade.repository.ScheduleRepository;
import com.example.schedulemanagementappupgrade.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateScheduleResponseDto createSchedule(Long userId, String title, String contents) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        Schedule schedule = new Schedule(user.getUserName(), title, contents);
        schedule.setUser(user);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new CreateScheduleResponseDto(savedSchedule.getId(), savedSchedule.getTitle(), savedSchedule.getContents());
    }

    public List<FindAllSchedulesWithUserIdResponseDto> findSchedules(Long userId) {
        List<Schedule> schedules = scheduleRepository.findByUserId(userId);

        return schedules.stream()
                .map(s -> new FindAllSchedulesWithUserIdResponseDto(s.getId(), s.getTitle(), s.getContents()))
                .toList();
    }

    public FindScheduleWithScheduleIdResponseDto findById(Long userId, Long scheduleId) {
        Schedule findSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule Not Found"));

        if (!findSchedule.getUser().getId().equals(userId)) {
            throw new ScheduleNotFoundException("No permission for this Schedule");
        }
        return new FindScheduleWithScheduleIdResponseDto(findSchedule.getId(), findSchedule.getTitle(), findSchedule.getContents());
    }

    @Transactional
    public void updateSchedule(Long userId, Long scheduleId, String title, String contents, String password) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule Not Found"));

        User user = schedule.getUser();
        if (user == null) throw new UserNotFoundException("User Not Found");
        if (!user.getId().equals(userId)) throw new ScheduleNotFoundException("No permission for this Schedule");
        if (!user.getPassword().equals(password)) throw new PasswordNotFoundException("Password is not correct");

        schedule.setTitle(title);
        schedule.setContents(contents);
    }

    @Transactional
    public void deleteSchedule(Long userId, Long scheduleId, String password) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule Not Found"));

        User user = schedule.getUser();
        if (user == null) throw new UserNotFoundException("User Not Found");
        if (!user.getId().equals(userId)) throw new ScheduleNotFoundException("No permission for this Schedule");
        if (!user.getPassword().equals(password)) throw new PasswordNotFoundException("Password is not correct");

        scheduleRepository.deleteById(scheduleId);
    }
}