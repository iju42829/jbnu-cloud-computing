package com.cloudbox.backend.file.service.interfaces.command;

import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.file.domain.File;
import org.springframework.web.multipart.MultipartFile;

public interface S3StorageCommandService {

    void deleteFile(File file);
    Long fileUpload(MemberSessionDto memberSessionDto, MultipartFile uploadFile, Long folderId);
}
