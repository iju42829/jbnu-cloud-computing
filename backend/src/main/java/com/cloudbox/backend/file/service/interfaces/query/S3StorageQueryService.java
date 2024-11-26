package com.cloudbox.backend.file.service.interfaces.query;

import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.file.dto.response.FileDownloadResponse;


public interface S3StorageQueryService {
    FileDownloadResponse downloadFile(MemberSessionDto memberSessionDto, Long fileId);
}
