package com.cloudbox.backend.file.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class FolderMoveOptionsResponse {
    private String currentFolderPath;
    private Long moveId;
    private Long parentFolderId;
    private Long currentFolderId;
    private List<FolderResponse> folders;

}
