package com.cloudbox.backend.member.auth.filter;

import com.cloudbox.backend.member.auth.constants.AuthConstants;
import com.cloudbox.backend.member.auth.dto.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.io.IOException;

public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    public JsonUsernamePasswordAuthenticationFilter(ObjectMapper objectMapper,
                                                    AuthenticationSuccessHandler successHandler,
                                                    AuthenticationFailureHandler failureHandler,
                                                    AuthenticationManager authenticationManager,
                                                    HttpSessionSecurityContextRepository httpSessionSecurityContextRepository) {

        this.objectMapper = objectMapper;

        setAuthenticationSuccessHandler(successHandler);
        setAuthenticationFailureHandler(failureHandler);
        setAuthenticationManager(authenticationManager);
        setSecurityContextRepository(httpSessionSecurityContextRepository);

        setFilterProcessesUrl(AuthConstants.DEFAULT_LOGIN_REQUEST_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        if (!request.getMethod().equalsIgnoreCase(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException("지원되지 않는 HTTP 메서드입니다: " + request.getMethod());
        }

        if (request.getContentType() == null || !request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            throw new AuthenticationServiceException("지원되지 않는 Content-Type 입니다: " + request.getContentType());
        }

        try {
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();

            if (username == null || password == null) {
                throw new AuthenticationServiceException("사용자 이름 또는 비밀번호가 누락되었습니다.");
            }

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            setDetails(request, authRequest);

            return this.getAuthenticationManager().authenticate(authRequest);

        } catch (IOException e) {
            throw new AuthenticationServiceException("JSON 요청을 파싱하는 데 실패했습니다: " + e.getMessage(), e);
        }
    }
}
