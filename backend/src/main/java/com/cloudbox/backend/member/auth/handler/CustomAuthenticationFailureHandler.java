package com.cloudbox.backend.member.auth.handler;

import com.cloudbox.backend.common.dto.Response;
import com.cloudbox.backend.member.auth.exception.JsonParsingException;
import com.cloudbox.backend.member.auth.exception.MissingCredentialsException;
import com.cloudbox.backend.member.auth.exception.UnsupportedContentTypeException;
import com.cloudbox.backend.member.auth.exception.UnsupportedHttpMethodException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Response responseResult;

        if (exception instanceof MissingCredentialsException) {
           responseResult = new Response(HttpServletResponse.SC_BAD_REQUEST, exception.getMessage());
           response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        } else if (exception instanceof UnsupportedHttpMethodException) {
            responseResult = new Response(HttpServletResponse.SC_METHOD_NOT_ALLOWED, exception.getMessage());
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

        } else if (exception instanceof UnsupportedContentTypeException) {
            responseResult = new Response(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, exception.getMessage());
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);

        } else if (exception instanceof JsonParsingException) {
            responseResult = new Response(HttpServletResponse.SC_BAD_REQUEST, exception.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            responseResult = new Response(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(objectMapper.writeValueAsString(responseResult));
        response.getWriter().flush();
    }
}
