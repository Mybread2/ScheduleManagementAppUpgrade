package com.example.schedulemanagementappupgrade.service;

import com.example.schedulemanagementappupgrade.config.PasswordEncoder;
import com.example.schedulemanagementappupgrade.dto.schedule.ScheduleCreationResponseDto;
import com.example.schedulemanagementappupgrade.dto.schedule.ScheduleResponseDto;
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
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ScheduleCreationResponseDto createSchedule(Long userId, String title, String contents) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        Schedule schedule = new Schedule(user, user.getUserName(), title, contents);
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleCreationResponseDto(savedSchedule.getId(), savedSchedule.getTitle(), savedSchedule.getContents());
    }

    public List<ScheduleResponseDto> findSchedules(Long userId) {
        List<Schedule> schedules = scheduleRepository.findByUserId(userId);

        return schedules.stream()
                .map(s -> new ScheduleResponseDto(s.getId(), s.getTitle(), s.getContents()))
                .toList();
    }

    public ScheduleResponseDto findById(Long userId, Long scheduleId) {
        Schedule findSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule Not Found"));

        if (!findSchedule.getUser().getId().equals(userId)) {
            throw new ScheduleNotFoundException("No permission for this Schedule");
        }
        return new ScheduleResponseDto(findSchedule.getId(), findSchedule.getTitle(), findSchedule.getContents());
    }

    @Transactional
    public void updateSchedule(Long userId, Long scheduleId, String title, String contents, String password) {

        String encodedPassword = passwordEncoder.encode(password);

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule Not Found"));

        User user = schedule.getUser();
        if (user == null) throw new UserNotFoundException("User Not Found");
        if (!user.getId().equals(userId)) throw new ScheduleNotFoundException("No permission for this Schedule");
        if (!user.getPassword().equals(encodedPassword)) throw new PasswordNotFoundException("Password is not correct");

        Schedule updatedSchedule = new Schedule(
                schedule.getId(),
                user,
                user.getUserName(),
                title,
                contents
        );

        scheduleRepository.save(updatedSchedule);
    }

    @Transactional
    public void deleteSchedule(Long userId, Long scheduleId, String password) {

        String encodedPassword = passwordEncoder.encode(password);

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule Not Found"));

        User user = schedule.getUser();
        if (user == null) throw new UserNotFoundException("User Not Found");
        if (!user.getId().equals(userId)) throw new ScheduleNotFoundException("No permission for this Schedule");
        if (!user.getPassword().equals(encodedPassword)) throw new PasswordNotFoundException("Password is not correct");

        scheduleRepository.deleteById(scheduleId);
    }
}