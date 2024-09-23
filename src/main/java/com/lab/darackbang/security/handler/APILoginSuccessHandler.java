package com.lab.darackbang.security.handler;

import com.google.gson.Gson;
import com.lab.darackbang.security.dto.LoginDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Log4j2
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        LoginDTO loginDTO = (LoginDTO) authentication.getPrincipal();
        Map<String, Object> claims = loginDTO.getClaims();

        Gson gson = new Gson();
        String jsonStr = gson.toJson(claims);

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonStr);
        printWriter.close();

        // 리디렉션 추가
        //response.sendRedirect("/api/home"); // 성공 후 리디렉션할 URL
    }
}
