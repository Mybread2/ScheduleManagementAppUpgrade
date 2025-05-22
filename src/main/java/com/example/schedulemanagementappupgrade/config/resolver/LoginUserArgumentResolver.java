package com.example.schedulemanagementappupgrade.config.resolver;

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

    public static final String USER_ID_SESSION_ATTRIBUTE_NAME = "userID";

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
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {
        // supportsParameter()가 true를 반환했을 때,
        // 스프링이 이 메서드를 호출해서 해당 파라미터에 어떤 값을 넣을지 결정

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);

        if (session == null) {
            throw new UnauthorizedException("User not authenticated.");
        }

        Object userIdAttribute = session.getAttribute(USER_ID_SESSION_ATTRIBUTE_NAME);

        if (userIdAttribute == null) {
            throw new UnauthorizedException("User not authenticated.");
        }

        if (!(userIdAttribute instanceof Long)) {
            throw new IllegalStateException("User ID in session is not of type Long.");
        }

        return userIdAttribute;
        // 로그인 시 세션에 저장된 "loginUser"라는 이름의 속성을 꺼내주어서
        // User 객체를 컨트롤러 메서드의 파라미터에 자동으로 주입해준다.
    }
}

