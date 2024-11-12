package com.cloudbox.backend.file.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "폴더 생성 요청 DTO")
public class FolderCreateRequest {

    @NotBlank
    @Schema(description = "폴더 이름")
    private String name;
}
