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
            "/users/login",
            "/users", // 회원가입: POST
            "/schedules/comments/*" // 댓글 조회(목록/단건 모두 허용)
    };


    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        // 회원가입과 로그인 요청시에만 필터 제외
        // 댓글 조회 기능시에도 필터 제외
        if (
                (requestURI.equals("/users") && method.equals("POST")) ||
                        (PatternMatchUtils.simpleMatch("/schedules/comments/*", requestURI) && method.equals("GET")) ||
                        isWhiteList(requestURI)
        ) {
            chain.doFilter(request, response);
            return;
        }


        // 화이트리스트 URL이 아니면 인증체크
        if (!isWhiteList(requestURI)) {
            HttpSession session = httpRequest.getSession(false);

            if (session == null || session.getAttribute("userId") == null) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write(" Login required. please login first.");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
