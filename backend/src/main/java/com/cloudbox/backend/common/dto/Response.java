package com.cloudbox.backend.common.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Response <T> {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private T data;

    private Response(int status, String message, T data) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> Response<T> createResponse(int status, String message, T data) {
        return new Response<>(status, message, data);
    }

    public static Response<?> createResponseWithoutData(int status, String message) {
        return new Response<>(status, message, null);
    }
}
