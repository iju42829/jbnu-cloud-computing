package com.cloudbox.backend.file.dto.response;

import com.cloudbox.backend.file.domain.Folder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class FolderResponse {
    private Long folderId;
    private String folderName;
    private String createBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public static FolderResponse fromFolder(Folder folder) {
        return new FolderResponse(folder.getId(), folder.getName(), folder.getCreateBy(), folder.getCreatedDate(), folder.getLastModifiedDate());
    }
}
