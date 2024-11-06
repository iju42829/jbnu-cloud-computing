package com.cloudbox.backend.member.auth.handler;

import com.cloudbox.backend.common.dto.Response;
import com.cloudbox.backend.member.auth.dto.AuthenticationSuccessResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Response<AuthenticationSuccessResponse> resultResponse = Response.createResponse(HttpServletResponse.SC_OK,
                "로그인에 성공했습니다.",
                new AuthenticationSuccessResponse(authentication.getName()));

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(objectMapper.writeValueAsString(resultResponse));
        response.getWriter().flush();
    }
}
