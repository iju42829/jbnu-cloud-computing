package com.cloudbox.backend.member.auth.exception;

import org.springframework.security.authentication.AuthenticationServiceException;

public class MissingCredentialsException extends AuthenticationServiceException {
    public MissingCredentialsException(String msg) {
        super(msg);
    }
}
