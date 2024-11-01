package com.cloudbox.backend.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class Response {
    private LocalDateTime timestamp;
    private int status;
    private String message;

    public Response(int status, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }
}
