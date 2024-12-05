package com.cloudbox.backend.file.dto.request;

import com.cloudbox.backend.file.service.ShareExpiration;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileShareCreateRequest {
    private Long fileId;
    private ShareExpiration shareExpiration;
}
