package com.cloudbox.backend.member.advice;

import com.cloudbox.backend.common.dto.Response;
import com.cloudbox.backend.member.exception.MemberNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MemberControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<Response<?>> handleMemberNotFoundException(MemberNotFoundException e) {
        log.warn("[ExceptionHandle] ex", e);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
