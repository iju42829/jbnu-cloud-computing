package com.cloudbox.backend.file.dto.response;

import com.cloudbox.backend.file.domain.FileShare;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileShareResponse {
    private Long fileShareId;
    private LocalDateTime expirationDate;
    private String fileName;
    private String createBy;
    private Long size;

    public static FileShareResponse fromFileShare(FileShare fileShare) {
        return new FileShareResponse(fileShare.getId(),
                fileShare.getExpirationDate(),
                fileShare.getFile().getFileName(),
                fileShare.getCreateBy(),
                fileShare.getFile().getSize());
    }

}
