package com.cloudbox.backend.file.service.interfaces.command;

import com.cloudbox.backend.common.dto.MemberSessionDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileCommandService {
    Long createFile(MemberSessionDto memberSessionDto, MultipartFile uploadFile, Long folderId);
    void deleteFile(MemberSessionDto memberSessionDto, Long fileId);
    void moveFile(MemberSessionDto memberSessionDto, Long fileId, Long targetFolderId);
}
