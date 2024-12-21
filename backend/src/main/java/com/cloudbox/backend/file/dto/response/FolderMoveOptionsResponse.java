package com.cloudbox.backend.file.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "폴더 이동 옵션 응답 DTO")
public class FolderMoveOptionsResponse {
    @Schema(description = "현재 폴더 경로")
    private String currentFolderPath;

    @Schema(description = "이동할 대상 ID (파일 또는 폴더 ID)")
    private Long moveId;

    @Schema(description = "부모 폴더 ID, 부모 폴더가 NULL이면 ROOT 폴더를 의미합니다.")
    private Long parentFolderId;

    @Schema(description = "현재 폴더 ID")
    private Long currentFolderId;

    @Schema(description = "이동 가능한 폴더 목록")
    private List<FolderResponse> folders;

}
