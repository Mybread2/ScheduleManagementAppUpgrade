package com.example.schedulemanagementappupgrade.repository;

import com.example.schedulemanagementappupgrade.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
