package com.example.schedulemanagementappupgrade.config;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component // 스프링 빈으로 등록
public class CustomHttpSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info("===== Session Created: ID = {}, CreationTime = {} =====",
                se.getSession().getId(),
                se.getSession().getCreationTime()); // 생성 시간도 함께 로깅
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info("===== Session Destroyed: ID = {}, LastAccessedTime = {} =====",
                se.getSession().getId(),
                se.getSession().getLastAccessedTime()); // 마지막 접근 시간도 함께 로깅
    }
}