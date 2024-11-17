package com.cloudbox.backend.file.dto.response;

import com.cloudbox.backend.file.domain.File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    private Long fileId;

    private String fileName;

    public static FileResponse fromFile(File file) {
        return new FileResponse(file.getId(), file.getFileName());
    }
}
