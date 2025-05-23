package com.example.schedulemanagementappupgrade.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        // 인가 실패 시 403 Forbidden 응답 설정
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // 클라이언트에게 보낼 JSON 응답 본문 구성
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", HttpStatus.FORBIDDEN.value());
        errorDetails.put("error", "Forbidden");
        errorDetails.put("message", "해당 리소스에 접근할 권한이 없습니다."); // 사용자에게 보여줄 메시지
        // accessDeniedException.getMessage()를 포함할 수도 있지만, 보안상 민감한 정보가 포함될 수 있으므로 주의
        // errorDetails.put("details", accessDeniedException.getMessage());


        // JSON 응답 작성
        objectMapper.writeValue(response.getWriter(), errorDetails);
    }
}