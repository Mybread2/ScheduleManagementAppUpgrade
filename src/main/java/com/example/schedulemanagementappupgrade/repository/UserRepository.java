package com.example.schedulemanagementappupgrade.repository;

import com.example.schedulemanagementappupgrade.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
