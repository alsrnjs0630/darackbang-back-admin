package com.lab.darackbang.security.handler;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

@Slf4j
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException {
        // 로그아웃 성공 후 추가 작업 수행
        log.info("로그아웃이 성공적으로 처리되었습니다.");

        // 리디렉션 설정(임시)
        response.sendRedirect("/api/products/list");
    }
}