package com.lab.darackbang.security.service;

import com.lab.darackbang.entity.Role;
import com.lab.darackbang.exception.RoleAccessDeniedException;
import com.lab.darackbang.security.dto.LoginDTO;
import com.lab.darackbang.entity.Member;
import com.lab.darackbang.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("사용자 로딩 중: {}", username);

        return memberRepository.findByUserEmail(username).map(user -> {

            // 권한 체크: 사용자의 역할에 "ROLE_MANAGER" 또는 "ROLE_ADMIN"이 있는지 확인
            if (user.getMemberRoles().stream().noneMatch(role -> EnumSet.of(Role.MANAGER, Role.ADMIN).contains(role.getRole()))) {
                log.info("사용자 {}에게 필요한 역할 (MANAGER 또는 ADMIN)이 없습니다", username);
                throw new UsernameNotFoundException("사용자에게 필요한 롤권한이 없습니다");
            }

            return new LoginDTO(
                    user.getUserEmail(),
                    user.getPassword(),
                    user.getName(),
                    user.getMemberRoles().stream()
                            .map(role -> "ROLE_" + role.getRole())  // ROLE_ 접두사 추가
                            .collect(Collectors.toList())
            );

        }).orElseThrow(() -> {
            log.info("사용자를 찾을 수 없음: {}", username);
            return new UsernameNotFoundException("사용자를 찾을 수 없음");
        });
    }
}

