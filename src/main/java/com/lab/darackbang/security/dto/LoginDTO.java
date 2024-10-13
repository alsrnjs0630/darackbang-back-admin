package com.lab.darackbang.security.dto;

import com.lab.darackbang.entity.MemberRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


/**
 * A DTO for the {@link org.springframework.security.core.userdetails.User} entity.
 */
@Schema(description = "로그인정보")
@Setter
@ToString
@Getter
@AllArgsConstructor
public class LoginDTO implements UserDetails {

    private static final long serialVersionUID = 1L;  // 직렬화 UID 명시적 설정

    private String userEmail;
    private String password;
    private String name;
    private List<String> roleNames;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleNames.stream()
                .map(SimpleGrantedAuthority::new)  // ROLE_ 중복되지 않도록 설정
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 기본 값 true
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 기본 값 true
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 기본 값 true
    }

    @Override
    public boolean isEnabled() {
        return true; // 기본 값 true
    }

    public Map<String, Object> getInfo() {
        Map<String, Object> dataMap = new HashMap<>();
        // roleNames에서 "ROLE_" 접두사를 제거
        List<String> cleanedRoles = roleNames.stream()
                .map(role -> role.replaceFirst("ROLE_", ""))  // "ROLE_" 접두사 제거
                .collect(Collectors.toList());

        dataMap.put("email", userEmail);
        dataMap.put("password", password);
        dataMap.put("nickname", name);
        dataMap.put("roleNames", cleanedRoles);
        return dataMap;
    }
}