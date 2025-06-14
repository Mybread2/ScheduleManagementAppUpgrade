package com.example.schedulemanagementappupgrade.repository;

import com.example.schedulemanagementappupgrade.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailAddress(String emailAddress);

    Optional<User> findByEmailAddress(String emailAddress);
}
