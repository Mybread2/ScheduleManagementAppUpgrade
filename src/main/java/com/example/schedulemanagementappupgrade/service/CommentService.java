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

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }

    private Schedule findScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule Not Found"));
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment Not Found."));
    }

    private void verifyUserPassword(User user, String rawPassword) {
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new PasswordNotFoundException("User password is not correct");
        }
    }

    private void validateCommentScheduleAndOwnership(Comment comment, Long scheduleId, Long userId) {
        if (!comment.getSchedule().getId().equals(scheduleId)) {
            throw new AccessDeniedException("Comment with id " + comment.getId() + " not found under schedule " + scheduleId);
        }
        if (!comment.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You do not have permission for this comment");
        }
    }

    @Transactional
    public CommentCreationResponseDto createComment(Long userId, Long scheduleId, String content) {
        User user = findUserById(userId);
        Schedule schedule = findScheduleById(scheduleId);

        Comment comment = new Comment(user, schedule, content);
        Comment savedComment = commentRepository.save(comment);

        return new CommentCreationResponseDto(savedComment.getId(), user.getUserName(), savedComment.getContent());
    }

    public List<CommentResponseDto> findComment(Long scheduleId) {
        findScheduleById(scheduleId);

        List<Comment> comments = commentRepository.findByScheduleId(scheduleId);

        return comments.stream()
                .map(s -> new CommentResponseDto(s.getId(), s.getUser().getUserName(),s.getContent()))
                .toList();
    }

    @Transactional
    public void updateComment(Long userId, Long scheduleId, Long commentId, String content, String userRawPassword) {
        User user = findUserById(userId);
        verifyUserPassword(user, userRawPassword);
        findScheduleById(scheduleId);
        Comment comment = findCommentById(commentId);
        validateCommentScheduleAndOwnership(comment, scheduleId, userId);

        comment.updateComment(content);
    }

    @Transactional
    public void deleteComment(Long userId, Long scheduleId, Long commentId, String userRawPassword) {
        User user = findUserById(userId);
        verifyUserPassword(user, userRawPassword);
        findScheduleById(scheduleId);
        Comment comment = findCommentById(commentId);
        validateCommentScheduleAndOwnership(comment, scheduleId, userId);

        commentRepository.delete(comment);
    }
}
