package com.cloudbox.backend.file.service.interfaces.query;

import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.file.domain.Folder;
import com.cloudbox.backend.file.dto.ResourceType;
import com.cloudbox.backend.file.dto.response.FolderMoveOptionsResponse;
import com.cloudbox.backend.file.dto.response.FolderResponse;

import java.util.List;

public interface FolderQueryService {
    String getFullFolderPathById(Long folderId);

    List<FolderResponse> getFolderResponsesById(Long folderId, MemberSessionDto memberSessionDto);

    Folder getFolderEntityByIdAndCreateBy(Long folderId, MemberSessionDto memberSessionDto);

    FolderMoveOptionsResponse getFolderResponsesWithoutMoveId(Long currentFolderId, Long moveId, ResourceType resourceType, MemberSessionDto memberSessionDto);

}
