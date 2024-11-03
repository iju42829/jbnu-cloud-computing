package com.cloudbox.backend.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    USER("USER"), ADMIN("ADMIN");

    private final String roleName;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
