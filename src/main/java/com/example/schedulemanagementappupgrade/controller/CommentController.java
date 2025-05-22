package com.example.schedulemanagementappupgrade.controller;

import com.example.schedulemanagementappupgrade.dto.comment.*;
import com.example.schedulemanagementappupgrade.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules/comments/{scheduleId}")
public class CommentController {

    private final CommentService commentService;

    private Long getLoginUserId(HttpServletRequest request) {
        return (Long) request.getSession(false).getAttribute("userId");
    }

    @PostMapping
    public ResponseEntity<CommentCreationResponseDto> createComment(
            @PathVariable Long scheduleId,
            @Valid @RequestBody CommentCreationRequestDto requestDto,
            HttpServletRequest request
    ) {
        Long userId = getLoginUserId(request);

        CommentCreationResponseDto createCommentResponseDto = commentService.createComment(
                userId,
                scheduleId,
                requestDto.getContent()
        );
        return ResponseEntity.ok(createCommentResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> GetMyComments(
            @PathVariable Long scheduleId
    ) {
        List<CommentResponseDto> commentResponseDto = commentService.findComment(scheduleId);

        return ResponseEntity.ok(commentResponseDto);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateRequestDto requestDto,
            HttpServletRequest request
    ) {
        Long userId = getLoginUserId(request);

        CommentResponseDto updatedComment = commentService.updateComment(
                userId,
                scheduleId,
                commentId,
                requestDto.getContent(),
                requestDto.getPassword()
        );
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @Valid@RequestBody CommentDeletionRequestDto requestDto,
            HttpServletRequest request
    ) {
        Long userId = getLoginUserId(request);
        commentService.deleteComment(userId, scheduleId,commentId, requestDto.getPassword());
        return ResponseEntity.ok().build();
    }

}
