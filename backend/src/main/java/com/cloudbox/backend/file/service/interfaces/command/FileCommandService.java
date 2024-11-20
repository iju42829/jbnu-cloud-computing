package com.cloudbox.backend.file.service.interfaces.command;

import com.cloudbox.backend.common.dto.MemberSessionDto;

public interface FileCommandService {
    Long createFile(MemberSessionDto memberSessionDto, String fileName, String realFilePath, Long folderId);
    void deleteFile(MemberSessionDto memberSessionDto, Long fileId);
}
