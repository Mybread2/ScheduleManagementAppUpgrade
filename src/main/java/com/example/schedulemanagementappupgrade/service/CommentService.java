package com.example.schedulemanagementappupgrade.service;

import com.example.schedulemanagementappupgrade.dto.comment.CommentResponseDto;
import com.example.schedulemanagementappupgrade.dto.comment.CommentCreationResponseDto;
import com.example.schedulemanagementappupgrade.entity.Comment;
import com.example.schedulemanagementappupgrade.entity.Schedule;
import com.example.schedulemanagementappupgrade.entity.User;
import com.example.schedulemanagementappupgrade.exception.*;
import com.example.schedulemanagementappupgrade.repository.CommentRepository;
import com.example.schedulemanagementappupgrade.repository.ScheduleRepository;
import com.example.schedulemanagementappupgrade.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }

    private Schedule getSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule Not Found"));
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment Not Found"));
    }

    private void checkPassword(User user, String rawPassword) {
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new PasswordNotFoundException("Incorrect password");
        }
    }

    private void validateOwnership(Comment comment, Long scheduleId, Long userId) {
        if (!comment.getSchedule().getId().equals(scheduleId)) {
            throw new AccessDeniedException("Comment not under this schedule");
        }
        if (!comment.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("No permission for this comment");
        }
    }

    @Transactional
    public CommentCreationResponseDto createComment(Long userId, Long scheduleId, String content) {
        User user = getUser(userId);
        Schedule schedule = getSchedule(scheduleId);

        Comment comment = new Comment(user, schedule, content);
        Comment savedComment = commentRepository.save(comment);

        return new CommentCreationResponseDto(savedComment.getId(), user.getUserName(), savedComment.getContent());
    }

    public List<CommentResponseDto> findComment(Long scheduleId) {
        getSchedule(scheduleId); // 유효성 검사용

        return commentRepository.findByScheduleId(scheduleId).stream()
                .map(c -> new CommentResponseDto(c.getId(), c.getUser().getUserName(), c.getContent()))
                .toList();
    }

    @Transactional
    public void updateComment(Long userId, Long scheduleId, Long commentId, String content, String rawPassword) {
        User user = getUser(userId);
        checkPassword(user, rawPassword);

        Comment comment = getComment(commentId);
        validateOwnership(comment, scheduleId, userId);

        comment.updateComment(content);
    }

    @Transactional
    public void deleteComment(Long userId, Long scheduleId, Long commentId, String rawPassword) {
        User user = getUser(userId);
        checkPassword(user, rawPassword);

        Comment comment = getComment(commentId);
        validateOwnership(comment, scheduleId, userId);
    }
}


