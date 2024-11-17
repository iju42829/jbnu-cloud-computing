package com.cloudbox.backend.file.service.interfaces.query;

import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.file.dto.response.FolderResponse;

import java.util.List;

public interface FolderQueryService {
    String getFullFolderPathById(Long folderId);

    List<FolderResponse> getFolderResponseById(Long folderId, MemberSessionDto memberSessionDto);
}
