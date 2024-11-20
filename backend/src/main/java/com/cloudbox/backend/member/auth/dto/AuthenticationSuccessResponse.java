package com.cloudbox.backend.member.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class AuthenticationSuccessResponse {
    private String username;
    private Long rootFolderId;
}
