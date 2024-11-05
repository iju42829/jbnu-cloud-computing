package com.cloudbox.backend.member.auth.exception;

import org.springframework.security.authentication.AuthenticationServiceException;

public class UnsupportedContentTypeException extends AuthenticationServiceException {
    public UnsupportedContentTypeException(String msg) {
        super(msg);
    }
}
