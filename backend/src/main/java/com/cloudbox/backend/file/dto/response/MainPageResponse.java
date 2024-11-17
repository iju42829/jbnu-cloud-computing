package com.cloudbox.backend.file.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MainPageResponse {
    private List<FolderResponse> folders;
    private List<FileResponse> files;
}
