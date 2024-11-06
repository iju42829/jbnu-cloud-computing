package com.cloudbox.backend.common.advice;

import com.cloudbox.backend.common.dto.FieldErrorResponse;
import com.cloudbox.backend.common.dto.Response;
import com.cloudbox.backend.common.exception.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<Response<List<FieldErrorResponse>>> handleInvalidRequestException(InvalidRequestException e) {
        log.warn("[ExceptionHandle]", e);

        List<FieldErrorResponse> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError ->
                        new FieldErrorResponse(fieldError.getField(), fieldError.getCode(), fieldError.getDefaultMessage()))
                .toList();

        return new ResponseEntity<>(Response.createResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), errors), HttpStatus.BAD_REQUEST);
    }

}
