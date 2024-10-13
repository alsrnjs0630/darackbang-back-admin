package com.lab.darackbang.security.handler;


import com.google.gson.Gson;
import com.lab.darackbang.security.dto.LoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException {

        response.setContentType("application/json; charset=UTF-8");

        try (PrintWriter writer = response.getWriter()) {
            writer.println(new Gson().toJson(Map.of("RESULT", "LOGOUT SUCCESS")));
        }
    }
}