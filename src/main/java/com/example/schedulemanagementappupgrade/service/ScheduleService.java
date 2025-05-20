package com.example.schedulemanagementappupgrade.service;

import com.example.schedulemanagementappupgrade.dto.CreateScheduleResponseDto;
import com.example.schedulemanagementappupgrade.entity.Schedule;
import com.example.schedulemanagementappupgrade.entity.User;
import com.example.schedulemanagementappupgrade.exception.PasswordNotFoundException;
import com.example.schedulemanagementappupgrade.exception.UserNotFoundException;
import com.example.schedulemanagementappupgrade.repository.ScheduleRepository;
import com.example.schedulemanagementappupgrade.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateScheduleResponseDto createSchedule(String userName, String password,String title, String contents) {
        
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
        
       if (!user.getPassword().equals(password)) {
           throw new PasswordNotFoundException("Password is not correct");
       }
           
           Schedule schedule = new Schedule(user.getUserName(), title, contents);
           schedule.setUser(user);
           
           Schedule savedSchedule = scheduleRepository.save(schedule);
           
           return new CreateScheduleResponseDto(savedSchedule.getId(), savedSchedule.getTitle(), savedSchedule.getContents());
       }
}
