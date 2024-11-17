package com.cloudbox.backend.file.service.interfaces.command;

import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.file.dto.request.FolderCreateRequest;

public interface FolderCommandService {
    Long createRootFolder(Long memberId, FolderCreateRequest folderCreateRequest);
    Long createFolder(Long parentFolderId, FolderCreateRequest folderCreateRequest, MemberSessionDto memberSessionDto);
}
