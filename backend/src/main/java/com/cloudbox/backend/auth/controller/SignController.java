package com.cloudbox.backend.auth.controller;

import com.cloudbox.backend.auth.dto.member.request.MemberCreateRequest;
import com.cloudbox.backend.auth.service.MemberService;
import com.cloudbox.backend.common.dto.Response;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원가입", description = "회원가입 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SignController  {
    private final MemberService memberService;

    @PostMapping(value = "/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201", description = "회원 가입 성공")
    public ResponseEntity<Response> addMember(@RequestBody MemberCreateRequest memberCreateRequest) {
        memberService.signUp(memberCreateRequest);

        return new ResponseEntity<>(new Response(HttpStatus.CREATED.value(), "회원가입이 성공적으로 완료되었습니다."),
                HttpStatus.CREATED);
    }
}
