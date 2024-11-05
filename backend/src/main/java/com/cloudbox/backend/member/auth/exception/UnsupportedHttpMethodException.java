package com.cloudbox.backend.member.auth.exception;

import org.springframework.security.authentication.AuthenticationServiceException;

public class UnsupportedHttpMethodException extends AuthenticationServiceException {
    public UnsupportedHttpMethodException(String msg) {
        super(msg);
    }
}
