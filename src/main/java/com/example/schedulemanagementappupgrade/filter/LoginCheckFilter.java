package com.example.schedulemanagementappupgrade.filter;

import com.example.schedulemanagementappupgrade.config.resolver.LoginUserArgumentResolver;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LoginCheckFilter implements Filter {

    private static final List<WhiteListEntry> WHITE_LIST_ENTRIES = Arrays.asList(
            new WhiteListEntry("POST", "/users"),
            new WhiteListEntry("GET", "/schedules/comments/*")
    );


    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        if (isWhitelisted(method, requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        // 화이트리스트에 없으면 인증 체크
        HttpSession session = httpRequest.getSession(false);

        if (session == null || session.getAttribute(LoginUserArgumentResolver.USER_ID_SESSION_ATTRIBUTE_NAME) == null) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.getWriter().write("{\"error\": \"Authentication Required\", \"message\": \"Login is required.\"}");
            return; // 인증 실패 시 여기서 응답하고 필터 체인 종료
        }
// 인증 성공 시
        chain.doFilter(request, response); // 다음 필터 또는 서블릿으로 요청 전달

    }

    private boolean isWhitelisted(String method, String requestURI) {
        for (WhiteListEntry entry : WHITE_LIST_ENTRIES) {
            boolean uriMatches = PatternMatchUtils.simpleMatch(entry.uriPattern(), requestURI);
            // entry.getMethod()가 null 이면 모든 메서드 허용, 그렇지 않으면 메서드 일치 여부 확인
            boolean methodMatches = entry.method() == null || entry.method().equalsIgnoreCase(method);

            if (uriMatches && methodMatches) {
                return true;
            }
        }
        return false;
    }


    private record WhiteListEntry(String method, String uriPattern) {
    }
}
