package com.cloudbox.backend.file.service;

import lombok.Getter;

@Getter
public enum ShareExpiration {
    ONE_HOUR(1),
    SIX_HOURS(6),
    TWELVE_HOURS(12),
    TWENTY_FOUR_HOURS(24);

    private final int hours;

    ShareExpiration(int hours) {
        this.hours = hours;
    }
}
