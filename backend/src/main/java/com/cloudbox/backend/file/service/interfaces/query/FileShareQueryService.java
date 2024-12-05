package com.cloudbox.backend.file.service.interfaces.query;

import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.file.domain.FileShare;
import com.cloudbox.backend.file.dto.response.FileShareResponseList;

public interface FileShareQueryService {
    boolean validateFileShare(Long fileId, String uuid);
    FileShareResponseList getSharedFiles(MemberSessionDto memberSessionDto);
    FileShare getFileShareEntityByIdAndCreateBy(MemberSessionDto memberSessionDto, Long fileShareId);
}
