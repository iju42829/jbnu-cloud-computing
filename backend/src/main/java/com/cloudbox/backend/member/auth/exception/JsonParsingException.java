package com.cloudbox.backend.member.auth.exception;

import org.springframework.security.authentication.AuthenticationServiceException;

public class JsonParsingException extends AuthenticationServiceException {
    public JsonParsingException(String msg) {
        super(msg);
    }

    public JsonParsingException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
