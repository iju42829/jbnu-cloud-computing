package com.cloudbox.backend.file.service.interfaces.query;

import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.file.dto.response.FileResponse;

import java.util.List;

public interface FileQueryService {
    String getFilePathById(Long fileId);
    List<FileResponse> getFileResponsesByFolder(Long folderId, MemberSessionDto memberSessionDto);
}
