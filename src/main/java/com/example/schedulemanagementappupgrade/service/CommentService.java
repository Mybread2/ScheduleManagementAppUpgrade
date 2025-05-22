package com.example.schedulemanagementappupgrade.service;

import com.example.schedulemanagementappupgrade.config.security.PasswordEncoder;
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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CommentCreationResponseDto createComment(Long userId, Long scheduleId, String content) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule Not Found"));

        Comment comment = new Comment(user, schedule, content);
        Comment savedComment = commentRepository.save(comment);

        return new CommentCreationResponseDto(savedComment.getId(), user.getUserName(), savedComment.getContent());
    }

    public List<CommentResponseDto> findComment(Long scheduleId) {

       scheduleRepository.findById(scheduleId)
               .orElseThrow(() -> new ScheduleNotFoundException("Schedule Not Found"));

        List<Comment> comments = commentRepository.findByScheduleId(scheduleId);

        return comments.stream()
                .map(s -> new CommentResponseDto(s.getId(), s.getUser().getUserName(),s.getContent()))
                .toList();
    }

    @Transactional
    public void updateComment(Long userId, Long scheduleId, Long commentId, String content, String userRawPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if(passwordEncoder.matches(userRawPassword, user.getPassword())){
            throw new PasswordNotFoundException("User password is not correct");
        }

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule Not Found"));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment Not Found."));

        // 댓글이 해당 일정에 속하는지 확인
        if(!comment.getSchedule().getId().equals(schedule.getId())){
            throw new AccessDeniedException("Comment with id " + commentId + " not found under schedule " + scheduleId);
        }
        if(!comment.getUser().getId().equals(userId)){
            throw new AccessDeniedException("You do not have permission to delete this comment");
        }

        Comment updatedComment = new Comment(
                comment.getId(),
                comment.getUser(),
                comment.getSchedule(),
                content
        );

        commentRepository.save(updatedComment);
    }

    @Transactional
    public void deleteComment(Long userId, Long scheduleId, Long commentId, String userRawPassword) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule Not Found"));

        if (passwordEncoder.matches(userRawPassword, user.getPassword())) {
            throw new PasswordNotFoundException("User password is not correct");
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment Not Found."));

        if (!comment.getSchedule().getId().equals(schedule.getId())) {
            throw new AccessDeniedException("Comment with id " + commentId + " not found under schedule " + scheduleId);
        }

        if (!comment.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You do not have permission to delete this comment");
        }

        commentRepository.delete(comment);
    }
}
