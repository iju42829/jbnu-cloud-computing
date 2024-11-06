package com.cloudbox.backend.member.controller;

import com.cloudbox.backend.member.controller.validator.MemberCreateRequestValidator;
import com.cloudbox.backend.member.dto.request.MemberCreateRequest;
import com.cloudbox.backend.member.service.MemberService;
import com.cloudbox.backend.common.dto.Response;
import com.cloudbox.backend.common.exception.InvalidRequestException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원가입", description = "회원가입 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SignController  {

    private final MemberService memberService;
    private final MemberCreateRequestValidator memberCreateRequestValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(memberCreateRequestValidator);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원 가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 회원가입 요청",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping(value = "/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<?>> addMember(@Validated @RequestBody MemberCreateRequest memberCreateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("회원 가입 요청 값이 올바르지 않습니다. 다시 확인해 주세요.", bindingResult);
        }

        memberService.signUp(memberCreateRequest);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpStatus.CREATED.value(), "회원가입이 성공적으로 완료되었습니다."),
                HttpStatus.CREATED);
    }
}
