package com.lab.darackbang.config;

import com.lab.darackbang.security.handler.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@Log4j2
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    // Spring Security 필터 체인을 정의하는 Bean
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        log.info("스프링 시큐리티 설정");

        return http
                // CORS 설정
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)  // 세션을 항상 생성하여 유지
                        .maximumSessions(1)  // 동시 접속을 1로 제한
                        .maxSessionsPreventsLogin(false)
                        .expiredSessionStrategy(new CustomSessionExpiredHandler())  // 세션 만료 처리 핸들러 설정// 세션 초과 시 새로운 로그인 허용
                )
                .csrf(AbstractHttpConfigurer::disable)
                // 로그인 설정
                .formLogin(login -> login
                        .loginPage("/admin/login")  // 커스텀 로그인 페이지 설정
                        .successHandler(new APILoginSuccessHandler())  // 로그인 성공 시 핸들러
                        .failureHandler(new APILoginFailHandler()))  // 로그인 실패 시 핸들러
                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")  // 로그아웃 URL
                        .clearAuthentication(true)
                        .addLogoutHandler((request, response, authentication) -> {
                            // 인증 정보를 삭제합니다
                            HttpSession session = request.getSession();
                            session.invalidate();  // 세션 무효화
                        })
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                )  // 쿠키 삭제
                // 인증 요청에 대한 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/logout").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/admin/products/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/admin/orders/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/admin/payments/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/admin/statistics/**").hasAnyRole("ADMIN", "MANAGER")
                        .anyRequest().authenticated())  // 그 외의 모든 요청은 인증 필요
                // 예외 처리 설정
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedHandler(new CustomAccessDeniedHandler()))
                // SecurityContext를 세션에 저장하도록 설정
                .securityContext(securityContext -> securityContext
                        .securityContextRepository(new HttpSessionSecurityContextRepository()))
                .build();
    }

    // CORS(Cross-Origin Resource Sharing) 설정을 정의하는 Bean
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 모든 도메인에서의 요청 허용
        configuration.setAllowedOriginPatterns(List.of("*"));
        // 허용할 HTTP 메서드 설정
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
        // 허용할 HTTP 헤더 설정
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        // 자격 증명(쿠키 등) 허용 설정
        configuration.setAllowCredentials(true);
        // 설정한 CORS 규칙을 모든 경로에 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //ORS(Cross-Origin Resource Sharing) 설정을 애플리케이션의 모든 경로에 적용하는 역할
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    // 비밀번호 암호화를 위한 PasswordEncoder Bean (BCryptPasswordEncoder 사용)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // HttpSessionEventPublisher Bean 추가 (세션 무효화 및 동시 세션 관리를 위한 설정)
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

}
