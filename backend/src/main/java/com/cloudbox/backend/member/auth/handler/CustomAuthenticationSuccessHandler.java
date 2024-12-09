package com.cloudbox.backend.member.auth.handler;

import com.cloudbox.backend.common.dto.Response;
import com.cloudbox.backend.file.domain.Folder;
import com.cloudbox.backend.file.dto.FolderType;
import com.cloudbox.backend.file.exception.FolderNotFoundException;
import com.cloudbox.backend.file.repository.FolderRepository;
import com.cloudbox.backend.member.auth.dto.AuthenticationSuccessResponse;
import com.cloudbox.backend.member.domain.Member;
import com.cloudbox.backend.member.exception.MemberNotFoundException;
import com.cloudbox.backend.member.repository.MemberRepository;
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
    private final FolderRepository folderRepository;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Member member = memberRepository.findByUsername(authentication.getName()).orElseThrow(() -> new MemberNotFoundException("해당 멤버를 찾을 수 없습니다."));
        Folder folder = folderRepository.findByMemberAndFolderType(member, FolderType.ROOT).orElseThrow(() -> new FolderNotFoundException("루트 폴더를 찾을 수 없습니다."));

        Response<AuthenticationSuccessResponse> resultResponse = Response.createResponse(HttpServletResponse.SC_OK,
                "로그인에 성공했습니다.",
                new AuthenticationSuccessResponse(authentication.getName(), folder.getId()));

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(objectMapper.writeValueAsString(resultResponse));
        response.getWriter().flush();
    }
}
