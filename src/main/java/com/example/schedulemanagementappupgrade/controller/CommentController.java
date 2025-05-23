package com.example.schedulemanagementappupgrade.controller;

import com.example.schedulemanagementappupgrade.dto.comment.*;
import com.example.schedulemanagementappupgrade.entity.User;
import com.example.schedulemanagementappupgrade.service.CommentService;
import com.example.schedulemanagementappupgrade.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules/comments/{scheduleId}")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<CommentCreationResponseDto> createComment(
            @PathVariable Long scheduleId,
            @Valid @RequestBody CommentCreationRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User currentUser = userService.findByEmailAddressOrThrow(userDetails.getUsername());
        CommentCreationResponseDto createCommentResponseDto = commentService.createComment(
                currentUser.getId(),
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
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User currentUser = userService.findByEmailAddressOrThrow(userDetails.getUsername());
        commentService.updateComment(
                currentUser.getId(), // 실제 사용자 ID 전달
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
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User currentUser = userService.findByEmailAddressOrThrow(userDetails.getUsername());
        commentService.deleteComment(
                currentUser.getId(), // 실제 사용자 ID 전달
                scheduleId,
                commentId,
                requestDto.getPassword());

        return ResponseEntity.noContent().build();
    }

}
