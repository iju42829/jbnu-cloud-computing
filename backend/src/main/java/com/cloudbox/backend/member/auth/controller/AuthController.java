package com.cloudbox.backend.member.auth.controller;

import com.cloudbox.backend.common.dto.Response;
import com.cloudbox.backend.member.auth.dto.AuthenticationSuccessResponse;
import com.cloudbox.backend.member.auth.dto.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "사용자 인증", description = "인증 관련 API")
public class AuthController {

    @Operation(summary = "로그아웃", description = "사용자를 로그아웃합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    })
    @PostMapping("/logout")
    public void logout() {
        // 실제 로그아웃 처리는 Spring Security에서 수행됩니다.
    }

    @Operation(summary = "로그인", description = "사용자 이름과 비밀번호로 인증합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패 - 잘못된 자격 증명", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "415", description = "지원하지 않는 메서드", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    })
    @PostMapping("/login")
    public Response<AuthenticationSuccessResponse> login(@RequestBody LoginRequest loginRequest) {
        // 실제 로그인 처리는 Spring Security에서 수행됩니다.
        return null;
    }
}