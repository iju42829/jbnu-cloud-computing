package com.cloudbox.backend.member.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class AuthTestController {

    @GetMapping("/user")
    public String testUserAuthentication() {
        return "사용자 인증이 성공적으로 완료되었습니다.";
    }

    @GetMapping("/admin")
    public String testAdminAuthentication() {
        return "관리자 인증이 성공적으로 완료되었습니다.";
    }
}
