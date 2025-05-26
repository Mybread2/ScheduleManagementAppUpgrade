package com.example.schedulemanagementappupgrade.controller;

import com.example.schedulemanagementappupgrade.dto.comment.*;
import com.example.schedulemanagementappupgrade.security.CustomUserDetails;
import com.example.schedulemanagementappupgrade.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules/comments/{scheduleId}")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentCreationResponseDto> createComment(
            @PathVariable Long scheduleId,
            @Valid @RequestBody CommentCreationRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        CommentCreationResponseDto responseDto = commentService.createComment(
                userDetails.user().getId(),
                scheduleId,
                requestDto.getContent()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long scheduleId) {
        List<CommentResponseDto> comments = commentService.findComment(scheduleId);
        return ResponseEntity.ok(comments);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        commentService.updateComment(
                userDetails.user().getId(),
                scheduleId,
                commentId,
                requestDto.getContent(),
                requestDto.getPassword()
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentDeletionRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        commentService.deleteComment(
                userDetails.user().getId(),
                scheduleId,
                commentId,
                requestDto.getPassword()
        );
        return ResponseEntity.noContent().build();
    }

}
