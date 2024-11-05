package com.cloudbox.backend.member.auth.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthConstants {
    public static final String DEFAULT_LOGIN_REQUEST_URL = "/api/login";
    public static final String DEFAULT_LOGOUT_REQUEST_URL = "/api/logout";
    public static final String SESSION_COOKIE_NAME = "JSESSIONID";
}
