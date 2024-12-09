package com.cloudbox.backend.file.advice;

import com.cloudbox.backend.common.dto.Response;
import com.cloudbox.backend.file.exception.FileNotFoundException;
import com.cloudbox.backend.file.exception.FileShareNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class FileControllerAdvice {

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Response<?>> handleFileNotFoundException(FileNotFoundException e) {
        log.warn("[ExceptionHandle] ex", e);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FileShareNotFoundException.class)
    public ResponseEntity<Response<?>> handleFileShareNotFoundException(FileShareNotFoundException e) {
        log.warn("[ExceptionHandle] ex", e);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
