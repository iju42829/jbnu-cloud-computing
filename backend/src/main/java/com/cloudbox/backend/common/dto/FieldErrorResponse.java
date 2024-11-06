package com.cloudbox.backend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldErrorResponse {
    private String field;
    private String code;
    private String message;
}
