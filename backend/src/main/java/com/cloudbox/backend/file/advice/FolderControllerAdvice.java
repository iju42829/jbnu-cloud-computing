package com.cloudbox.backend.file.advice;

import com.cloudbox.backend.common.dto.Response;
import com.cloudbox.backend.file.exception.FolderNotFoundException;
import com.cloudbox.backend.file.exception.RootFolderMoveException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class FolderControllerAdvice {

    @ExceptionHandler(FolderNotFoundException.class)
    public ResponseEntity<Response<?>> handleFolderNotFoundException(FolderNotFoundException e) {
        log.warn("[ExceptionHandle] ex", e);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RootFolderMoveException.class)
    public ResponseEntity<Response<?>> handleRootFolderMoveException(RootFolderMoveException e) {
        log.warn("[ExceptionHandle] ex", e);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
