package com.example.schedulemanagementappupgrade.controller;

import com.example.schedulemanagementappupgrade.config.annotation.LoginUser;
import com.example.schedulemanagementappupgrade.dto.comment.*;
import com.example.schedulemanagementappupgrade.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules/comments/{scheduleId}")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentCreationResponseDto> createComment(
            @PathVariable Long scheduleId,
            @Valid @RequestBody CommentCreationRequestDto requestDto,
            @LoginUser Long userId
    ) {
        CommentCreationResponseDto createCommentResponseDto = commentService.createComment(
                userId,
                scheduleId,
                requestDto.getContent()
        );
        return new ResponseEntity<>(createCommentResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> GetMyComments(
            @PathVariable Long scheduleId
    ) {
        List<CommentResponseDto> commentResponseDto = commentService.findComment(scheduleId);
        return ResponseEntity.ok(commentResponseDto);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateRequestDto requestDto,
            @LoginUser Long userId
    ) {
        commentService.updateComment(
                userId,
                scheduleId,
                commentId,
                requestDto.getContent(),
                requestDto.getPassword());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentDeletionRequestDto requestDto,
            @LoginUser Long userId
    ) {
        commentService.deleteComment(
                userId,
                scheduleId,
                commentId,
                requestDto.getPassword());

        return ResponseEntity.noContent().build();
    }

}
