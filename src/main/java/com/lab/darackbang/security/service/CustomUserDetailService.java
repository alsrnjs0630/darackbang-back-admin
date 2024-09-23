package com.lab.darackbang.security.service;

import com.lab.darackbang.security.dto.LoginDTO;
import com.lab.darackbang.entity.Member;
import com.lab.darackbang.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    // 의존성 주입을 통해 MemberRepository 사용
    private final MemberRepository memberRepository;

    /**
     * 사용자의 이메일(username)로 회원 정보를 로드하는 메서드
     * @param username 사용자의 이메일
     * @return UserDetails 사용자 인증 정보를 포함한 UserDetails 객체
     * @throws UsernameNotFoundException 사용자가 없을 경우 예외 발생
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", username);

        // 이메일로 사용자를 찾고, 없으면 예외를 던짐
        return memberRepository.findByUserEmail(username).map(this::createLoginDTO)
                .orElseThrow(() -> {
                    log.info("User not found: {}", username); // 사용자 없음을 로그로 기록
                    return new UsernameNotFoundException("User not found"); // 예외 발생
                });
    }

    /**
     * Member 객체를 기반으로 LoginDTO를 생성하는 메서드
     * @param member 사용자 엔티티
     * @return LoginDTO 사용자 정보와 권한을 담은 DTO 객체
     */
    private LoginDTO createLoginDTO(Member member) {
        // 사용자의 역할(Role)을 로그로 출력
        log.info("User roles: {}", member.getMemberRoles().stream()
                .map(role -> role.getRole().toString())
                .collect(Collectors.joining(", ")));

        // Member 객체에서 정보를 추출해 LoginDTO 객체로 변환
        return new LoginDTO(
                member.getUserEmail(), // 사용자의 이메일
                member.getPassword(),  // 암호화된 비밀번호
                member.getName(),      // 사용자 이름
                // 사용자의 역할 목록을 문자열 리스트로 변환
                member.getMemberRoles().stream()
                        .map(role -> role.getRole().toString())
                        .collect(Collectors.toList())
        );
    }
}
