package com.example.schedulemanagementappupgrade.service;

import com.example.schedulemanagementappupgrade.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public record UserDetailsImpl(User user) implements UserDetails {

    @Override
    public String getPassword() {
        return user.getPassword(); // User 엔티티의 비밀번호 반환
    }

    @Override
    public String getUsername() {
        return user.getEmailAddress(); // User 엔티티의 이메일 주소를 username 으로 사용
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료되지 않음
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠기지 않음
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명(비밀번호) 만료되지 않음
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화됨
    }
}