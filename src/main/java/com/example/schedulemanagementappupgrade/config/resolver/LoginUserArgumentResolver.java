package com.example.schedulemanagementappupgrade.config.resolver;

import com.example.schedulemanagementappupgrade.config.annotation.LoginUser;
import com.example.schedulemanagementappupgrade.exception.UnauthorizedException;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    public static final String USER_ID_SESSION_ATTRIBUTE_NAME = "userId";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //  ArgumentResolver가 어떤 파라미터를 처리할 수 있는지 판단하는 메서드

        return parameter.hasParameterAnnotation(LoginUser.class) &&
                Long.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
            @NonNull MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            @NonNull NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {
        // supportsParameter()가 true를 반환했을 때,
        // 스프링이 이 메서드를 호출해서 해당 파라미터에 어떤 값을 넣을지 결정

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);

        if (session == null) {
            // 필터에서 이미 처리되었어야하지만, 방어적으로 체크
            throw new UnauthorizedException("User not authenticated.");
        }

        Object userIdAttribute = session.getAttribute(USER_ID_SESSION_ATTRIBUTE_NAME);

        if (userIdAttribute == null) {
            throw new UnauthorizedException("User not authenticated.");
        }

        // 타입 체크 및 변환
        if (!(userIdAttribute instanceof Long)) {
            // 세션에 저장된 userId의 타입이 Long이 아닌 경우, 심각한 문제일 수 있음
            throw new IllegalStateException("User ID in session is not of type Long. Actual type: " + userIdAttribute.getClass().getName());
        }

        return userIdAttribute; // 명시적으로 Long 타입 반환
    }
}

