package com.example.schedulemanagementappupgrade.repository;

import com.example.schedulemanagementappupgrade.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
