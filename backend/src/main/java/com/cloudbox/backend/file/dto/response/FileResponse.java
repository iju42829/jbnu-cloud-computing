package com.cloudbox.backend.file.dto.response;

import com.cloudbox.backend.file.domain.File;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "파일 응답 DTO")
public class FileResponse {
    @Schema(description = "파일 ID")
    private Long fileId;

    @Schema(description = "파일 이름")
    private String fileName;

    @Schema(description = "파일 생성자")
    private String createBy;

    @Schema(description = "파일 생성 날짜")
    private LocalDateTime createdDate;

    @Schema(description = "파일 수정 날짜")
    private LocalDateTime lastModifiedDate;

    @Schema(description = "파일 크기 (바이트)")
    private Long size;

    public static FileResponse fromFile(File file) {
        return new FileResponse(file.getId(), file.getFileName(), file.getCreateBy(), file.getCreatedDate(), file.getLastModifiedDate(), file.getSize());
    }
}
