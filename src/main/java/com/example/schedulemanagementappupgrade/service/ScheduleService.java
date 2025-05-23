package com.example.schedulemanagementappupgrade.service;

import com.example.schedulemanagementappupgrade.dto.schedule.ScheduleCreationResponseDto;
import com.example.schedulemanagementappupgrade.dto.schedule.ScheduleResponseDto;
import com.example.schedulemanagementappupgrade.entity.Schedule;
import com.example.schedulemanagementappupgrade.entity.User;
import com.example.schedulemanagementappupgrade.exception.AccessDeniedException;
import com.example.schedulemanagementappupgrade.exception.PasswordNotFoundException;
import com.example.schedulemanagementappupgrade.exception.ScheduleNotFoundException;
import com.example.schedulemanagementappupgrade.exception.UserNotFoundException;
import com.example.schedulemanagementappupgrade.repository.ScheduleRepository;
import com.example.schedulemanagementappupgrade.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }

    private Schedule findScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule Not Found"));
    }

    private void verifyUserPassword(User user, String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordNotFoundException("Password is not correct");
        }
    }

    private void checkScheduleOwnership(Schedule schedule,Long userId) {
        if (!schedule.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("No permission for this Schedule");
        }
    }

    @Transactional
    public ScheduleCreationResponseDto createSchedule(Long userId, String title, String contents) {
        User user = findUserById(userId);

        Schedule schedule = new Schedule(user, user.getUserName(), title, contents);
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleCreationResponseDto(savedSchedule.getId(), savedSchedule.getTitle(), savedSchedule.getContents());
    }

    public List<ScheduleResponseDto> findSchedules(Long userId) {
        List<Schedule> schedules = scheduleRepository.findByUserId(userId);

        return schedules.stream()
                .map(s -> new ScheduleResponseDto(s.getId(), s.getUserName() ,s.getTitle(), s.getContents()))
                .toList();
    }

    public ScheduleResponseDto findById(Long userId, Long scheduleId) {
        Schedule findSchedule = findScheduleById(scheduleId);
        checkScheduleOwnership(findSchedule, userId);
        return new ScheduleResponseDto(findSchedule.getId(), findSchedule.getUserName() ,findSchedule.getTitle(), findSchedule.getContents());
    }

    @Transactional
    public void updateSchedule(Long userId, Long scheduleId, String title, String contents, String password) {
        User user = findUserById(userId);
        verifyUserPassword(user, password);
        Schedule schedule = findScheduleById(scheduleId);
        checkScheduleOwnership(schedule, userId);
        schedule.updateSchedule(title, contents);
    }

    @Transactional
    public void deleteSchedule(Long userId, Long scheduleId, String password) {
        User user = findUserById(userId);
        verifyUserPassword(user, password);
        Schedule schedule = findScheduleById(scheduleId);
        checkScheduleOwnership(schedule, userId);
        scheduleRepository.delete(schedule);
    }
}