package com.cloudbox.backend.file.dto.response;

import com.cloudbox.backend.file.domain.File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    private Long fileId;
    private String fileName;
    private String createBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Long size;

    public static FileResponse fromFile(File file) {
        return new FileResponse(file.getId(), file.getFileName(), file.getCreateBy(), file.getCreatedDate(), file.getLastModifiedDate(), file.getSize());
    }
}
