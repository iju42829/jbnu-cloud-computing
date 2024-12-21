package com.cloudbox.backend.file.dto.response;

import com.cloudbox.backend.file.domain.Folder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "폴더 응답 DTO")
public class FolderResponse {
    @Schema(description = "폴더 ID")
    private Long folderId;

    @Schema(description = "폴더 이름")
    private String folderName;

    @Schema(description = "폴더 생성자")
    private String createBy;

    @Schema(description = "폴더 생성 날짜")
    private LocalDateTime createdDate;

    @Schema(description = "폴더 수정 날짜")
    private LocalDateTime lastModifiedDate;

    public static FolderResponse fromFolder(Folder folder) {
        return new FolderResponse(folder.getId(), folder.getName(), folder.getCreateBy(), folder.getCreatedDate(), folder.getLastModifiedDate());
    }
}
