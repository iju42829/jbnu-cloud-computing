package com.cloudbox.backend.file.dto.request;

import com.cloudbox.backend.file.service.ShareExpiration;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema(description = "파일 공유 요청 DTO")
public class FileShareCreateRequest {
    @Schema(description = "파일 고유 ID")
    private Long fileId;

    @Schema(description = "공유 파일 만료 시간 설정")
    private ShareExpiration shareExpiration;
}
