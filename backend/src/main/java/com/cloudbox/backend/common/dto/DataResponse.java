package com.cloudbox.backend.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DataResponse extends Response {
    private Object data;

    public DataResponse(int status, String message, Object data) {
        super(status, message);
        this.data = data;
    }
}
