package com.cloudbox.backend.file.dto.response;

import com.cloudbox.backend.file.domain.FileShare;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "파일 공유 응답 DTO")
public class FileShareResponse {
    @Schema(description = "파일 공유 ID")
    private Long fileShareId;

    @Schema(description = "공유 만료 날짜")
    private LocalDateTime expirationDate;

    @Schema(description = "공유 UUID")
    private String uuid;

    @Schema(description = "파일 ID")
    private Long fileId;

    @Schema(description = "파일 이름")
    private String fileName;

    @Schema(description = "파일 생성자")
    private String createBy;

    @Schema(description = "파일 크기 (바이트)")
    private Long size;

    public static FileShareResponse fromFileShare(FileShare fileShare) {
        return new FileShareResponse(fileShare.getId(),
                fileShare.getExpirationDate(),
                fileShare.getUuid(),
                fileShare.getFile().getId(),
                fileShare.getFile().getFileName(),
                fileShare.getCreateBy(),
                fileShare.getFile().getSize());
    }

}
