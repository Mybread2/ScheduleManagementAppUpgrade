package com.example.schedulemanagementappupgrade.service;

import com.example.schedulemanagementappupgrade.dto.auth.LoginRequestDto;
import com.example.schedulemanagementappupgrade.dto.auth.LoginResponseDto;
import com.example.schedulemanagementappupgrade.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public LoginResponseDto login(LoginRequestDto loginRequestDto, HttpServletRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmailAddress(),
                        loginRequestDto.getPassword()
                )
        );

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);

        HttpSession session = request.getSession(true);
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                context
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByEmailAddressOrThrow(userDetails.getUsername());

        return new LoginResponseDto(user.getUserName(), "로그인에 성공하셨습니다.");
    }
}