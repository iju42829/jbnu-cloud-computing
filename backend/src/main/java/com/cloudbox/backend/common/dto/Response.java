package com.cloudbox.backend.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Response <T> {
    @Schema(description = "응답 시간")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP 상태 코드")
    private int status;

    @Schema(description = "응답 메시지")
    private String message;

    @Schema(description = "응답 데이터")
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
