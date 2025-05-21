package com.example.schedulemanagementappupgrade.controller;

import com.example.schedulemanagementappupgrade.dto.comment.CommentResponseDto;
import com.example.schedulemanagementappupgrade.dto.comment.CreateCommentRequestDto;
import com.example.schedulemanagementappupgrade.dto.comment.CreateCommentResponseDto;
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
    public ResponseEntity<CreateCommentResponseDto> createComment(
            @PathVariable Long scheduleId,
            @Valid @RequestBody CreateCommentRequestDto requestDto,
            HttpServletRequest request
    ) {
        Long userId = getLoginUserId(request);

        CreateCommentResponseDto createCommentResponseDto = commentService.createComment(
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

}
