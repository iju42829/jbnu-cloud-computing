package com.cloudbox.backend.common.controller;

import com.cloudbox.backend.common.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "테스트", description = "서버 연결 및 인증 확인을 위한 API")
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Operation(summary = "서버 연결 상태 확인")
    @ApiResponse(responseCode = "200", description = "서버 연결 성공")
    @GetMapping
    public ResponseEntity<Response<?>> checkServerConnection() {
        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_OK, "서버 연결에 성공했습니다."), HttpStatus.OK);
    }

    @Operation(summary = "사용자 인증 테스트")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용자 인증 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 부족")
    })
    @GetMapping("/user")
    public ResponseEntity<Response<?>> testUserAuthentication() {
        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_OK,"사용자 인증 테스트가 성공적으로 완료되었습니다."), HttpStatus.OK);
    }

    @Operation(summary = "관리자 인증 테스트")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "관리자 인증 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요"),
            @ApiResponse(responseCode = "403", description = "권한 부족")
    })
    @GetMapping("/admin")
    public ResponseEntity<Response<?>> testAdminAuthentication() {
        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_OK,"관리자 인증 테스트가 성공적으로 완료되었습니다."), HttpStatus.OK);
    }
}
