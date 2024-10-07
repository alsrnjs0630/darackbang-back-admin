package com.lab.darackbang.config;

import com.lab.darackbang.security.handler.APILoginFailHandler;
import com.lab.darackbang.security.handler.APILoginSuccessHandler;
import com.lab.darackbang.security.handler.CustomAccessDeniedHandler;
import com.lab.darackbang.security.handler.CustomLogoutSuccessHandler;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
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
                // 세션 관리 설정 (JWT 사용 및 세션 상태 없음으로 설정)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 세션을 상태 없음으로 설정 (JWT 사용을 위해)
                        .maximumSessions(1)  // 동시에 하나의 세션만 허용
                        .expiredUrl("/login?expired"))  // 세션 만료 시 리디렉션할 URL
                // CSRF 방지 비활성화 (JWT 방식에서 보통 사용하지 않음)
                .csrf(AbstractHttpConfigurer::disable)
                // 로그인 설정
                .formLogin(login -> login
                        .loginPage("/admin/login")  // 커스텀 로그인 페이지 설정
                        .successHandler(new APILoginSuccessHandler())  // 로그인 성공 시 핸들러
                        .failureHandler(new APILoginFailHandler()))  // 로그인 실패 시 핸들러
                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")  // 로그아웃 URL
                        .addLogoutHandler((request, response, authentication) -> {
                            // Clear the authentication information
                            HttpSession session = request.getSession();
                            session.invalidate();  // 세션 무효화
                        })
                        .clearAuthentication(true)
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                        .deleteCookies("JSESSIONID", "access_token"))  // 쿠키 삭제
                // 인증 요청에 대한 권한 설정
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/admin/logout").permitAll()// 로그아웃 요청은 인증 없이 허용
                                .requestMatchers("/admin/products/**").permitAll()// 상품리스트 요청은 인증 없이 허용
                                .requestMatchers("/admin/members/**").permitAll()// 상품리스트 요청은 인증 없이 허용
                        /*.requestMatchers("/api/products/**").hasAnyRole("USER", "MANAGER","ADMIN") // 상품리스틑 요청은 해당롤만 허용 */)
                // 예외 처리 설정
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedHandler(new CustomAccessDeniedHandler()))  // 접근 권한 없을 시 처리 핸들러
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
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    // 비밀번호 암호화를 위한 PasswordEncoder Bean (BCryptPasswordEncoder 사용)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 세션 이벤트를 처리하고 세션의 상태를 추적하는 Bean (HttpSessionEventPublisher 사용)
    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }

    // 세션 생성 및 종료 시 동작을 정의하는 HttpSessionListener Bean
    @Bean
    public HttpSessionListener httpSessionListener() {
        return new HttpSessionListener() {
            // 세션 생성 시 타임아웃 설정 (1800초 = 30분)
            @Override
            public void sessionCreated(HttpSessionEvent se) {
                se.getSession().setMaxInactiveInterval(1800);  // 세션 타임아웃 30분 설정
            }

            // 세션 종료 시 동작 (필요 시 정의)
            @Override
            public void sessionDestroyed(HttpSessionEvent se) {
                // 세션 종료 시 별도 작업 필요할 경우 정의 가능
            }
        };
    }
}