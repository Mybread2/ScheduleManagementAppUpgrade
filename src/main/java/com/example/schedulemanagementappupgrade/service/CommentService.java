package com.example.schedulemanagementappupgrade.service;

import com.example.schedulemanagementappupgrade.dto.comment.CommentResponseDto;
import com.example.schedulemanagementappupgrade.dto.comment.CreateCommentResponseDto;
import com.example.schedulemanagementappupgrade.entity.Comment;
import com.example.schedulemanagementappupgrade.entity.Schedule;
import com.example.schedulemanagementappupgrade.entity.User;
import com.example.schedulemanagementappupgrade.exception.CommentNotFoundException;
import com.example.schedulemanagementappupgrade.exception.PasswordNotFoundException;
import com.example.schedulemanagementappupgrade.exception.ScheduleNotFoundException;
import com.example.schedulemanagementappupgrade.exception.UserNotFoundException;
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

    @Transactional
    public CreateCommentResponseDto createComment(Long userId, Long scheduleID, String content) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        Schedule schedule = scheduleRepository.findById(scheduleID)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule Not Found"));

        Comment comment = new Comment(user, schedule, content);
        Comment savedComment = commentRepository.save(comment);

        return new CreateCommentResponseDto(savedComment.getId(), user.getUserName(), savedComment.getContent());
    }

    public List<CommentResponseDto> findComment(Long scheduleId) {
        List<Comment> comments = commentRepository.findByScheduleId(scheduleId);

        return comments.stream()
                .map(s -> new CommentResponseDto(s.getId(), s.getContent()))
                .toList();
    }

    @Transactional
    public void deleteComment(Long userId, Long scheduleId ,Long commentId, String password) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment Not Found."));

        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalStateException("You can only delete comments you have written.");
        }
        if (!comment.getSchedule().getId().equals(scheduleId)) {
            throw new IllegalArgumentException("This comment does not belong to the schedule.");
        }

        if (!comment.getUser().getPassword().equals(password)) {
            throw new PasswordNotFoundException("Password is not correct");
        }

        commentRepository.deleteById(commentId);
    }
}
