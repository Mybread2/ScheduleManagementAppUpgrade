package com.example.schedulemanagementappupgrade.service;

import com.example.schedulemanagementappupgrade.dto.schedule.CreateScheduleResponseDto;
import com.example.schedulemanagementappupgrade.dto.schedule.FindAllSchedulesWithUserIdResponseDto;
import com.example.schedulemanagementappupgrade.dto.schedule.FindScheduleWithScheduleIdResponseDto;
import com.example.schedulemanagementappupgrade.entity.Schedule;
import com.example.schedulemanagementappupgrade.entity.User;
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
    public CreateScheduleResponseDto createSchedule(Long userId,String title, String contents) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

           Schedule schedule = new Schedule(user.getUserName(), title, contents);
           schedule.setUser(user);

           Schedule savedSchedule = scheduleRepository.save(schedule);

           return new CreateScheduleResponseDto(savedSchedule.getId(), savedSchedule.getTitle(), savedSchedule.getContents());
       }

    public List<FindAllSchedulesWithUserIdResponseDto> findSchedulesByUserId(Long userId) {
        List<Schedule> schedules = scheduleRepository.findByUserId(userId);

        return schedules.stream()
                .map(s -> new FindAllSchedulesWithUserIdResponseDto(s.getId(), s.getTitle(), s.getContents()))
                .toList();
    }

    public FindScheduleWithScheduleIdResponseDto findById(Long id) {
        Schedule findSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule Not Found"));

        return new FindScheduleWithScheduleIdResponseDto(findSchedule.getId(), findSchedule.getTitle(), findSchedule.getContents());
    }

    @Transactional
    public void updateSchedule(Long id, String title, String contents, String password) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule Not Found"));

        User user = schedule.getUser();
        if(user == null) throw new UserNotFoundException("User Not Found");
        if(!user.getPassword().equals(password)) throw new UserNotFoundException("Password is not correct");

        schedule.setTitle(title);
        schedule.setContents(contents);
    }

    @Transactional
    public void deleteSchedule(Long id, String password) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule Not Found"));

        User user = schedule.getUser();
        if(!user.getPassword().equals(password)) throw new UserNotFoundException("Password is not correct");

        scheduleRepository.deleteById(id);
    }
}
