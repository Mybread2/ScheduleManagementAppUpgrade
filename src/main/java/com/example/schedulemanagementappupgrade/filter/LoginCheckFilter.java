package com.example.schedulemanagementappupgrade.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

public class LoginCheckFilter implements Filter {

    private static final String[] WHITE_LIST = {
            "/auth/login",
            "/auth/logout",
            "/"
    };

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        // 회원가입(Post /users)은 인증 없이 허용
        if (requestURI.equals("/users") && method.equals("POST")) {
            chain.doFilter(request, response);
            return;
        }

        // 로그인을 체크해야하는 URL 인지 검사
        // whiteListURL에 포함된 경우 true 반환
        if (!isWhiteList(requestURI)) {
            HttpSession session = httpRequest.getSession(false);

            if (session == null || session.getAttribute("userId") == null) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("{\"message\": \"Login required\"}");
                return;
            }

        }
        // 로그인 성공 로직

        chain.doFilter(request, response);
    }

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
