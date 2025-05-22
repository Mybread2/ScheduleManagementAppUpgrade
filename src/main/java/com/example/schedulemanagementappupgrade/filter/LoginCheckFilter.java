package com.example.schedulemanagementappupgrade.filter;

import com.example.schedulemanagementappupgrade.config.resolver.LoginUserArgumentResolver;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class LoginCheckFilter implements Filter {

    private static final List<WhiteListEntry> WHITE_LIST_ENTRIES = Arrays.asList(
            new WhiteListEntry("POST", "/users"),
            new WhiteListEntry("GET", "/schedules/comments/*"),
            new WhiteListEntry("POST", "/auth/login")
    );

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        if (isWhitelisted(method, requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);

        if (session == null || session.getAttribute(LoginUserArgumentResolver.USER_ID_SESSION_ATTRIBUTE_NAME) == null) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"message\": \"Login is required.\"}");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isWhitelisted(String method, String requestURI) {
        for (WhiteListEntry entry : WHITE_LIST_ENTRIES) {
            boolean uriMatches = PatternMatchUtils.simpleMatch(entry.uriPattern(), requestURI);
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

