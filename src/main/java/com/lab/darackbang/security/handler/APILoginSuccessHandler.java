package com.lab.darackbang.security.handler;

import com.google.gson.Gson;
import com.lab.darackbang.security.dto.LoginDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


@Slf4j
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {


        log.info("로그인 성공...");
        log.info("사용자 권한: {}", authentication.getAuthorities());

        if (authentication.getPrincipal() instanceof LoginDTO loginDTO) {
            log.info("사용자 객체: {}", loginDTO);

            response.setContentType("application/json; charset=UTF-8");
            try (PrintWriter writer = response.getWriter()) {
                writer.println(new Gson().toJson(loginDTO.getInfo()));
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json; charset=UTF-8");
            try (PrintWriter writer = response.getWriter()) {
                writer.println(new Gson().toJson(Map.of("error", "ACCESSDENIED")));
            }
        }
    }
}
