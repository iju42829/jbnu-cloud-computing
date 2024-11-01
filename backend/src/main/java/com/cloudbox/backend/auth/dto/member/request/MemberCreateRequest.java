package com.cloudbox.backend.auth.dto.member.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "회원 가입 요청 DTO")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreateRequest {

    @NotBlank
    @Schema(description = "사용자 아이디")
    private String username;

    @NotBlank
    @Size(min = 8, max = 15)
    @Schema(description = "비밀번호")
    private String password;

    @NotBlank
    @Size(min = 8, max = 15)
    @Schema(description = "비밀번호 확인")
    private String confirmPassword;

    @Email
    @NotBlank
    @Schema(description = "사용자 이메일 주소")
    private String email;
}
