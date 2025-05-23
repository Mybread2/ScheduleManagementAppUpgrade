package com.example.schedulemanagementappupgrade.service;

import com.example.schedulemanagementappupgrade.entity.User;
import com.example.schedulemanagementappupgrade.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        log.info("[AUTH] Attempting to load user by email: {}", emailAddress); // 로그 강화

        User user = userRepository.findByEmailAddress(emailAddress)
                .orElseThrow(() -> {
                    log.warn("[AUTH] User not found with email: {}", emailAddress);
                    return new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + emailAddress);
                });

        log.info("[AUTH] User found: {}. Creating UserDetailsImpl.", user.getEmailAddress());
        return new UserDetailsImpl(user);
    }
}
