package com.cloudbox.backend.file.service.interfaces.command;

import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.file.dto.request.FileShareCreateRequest;

public interface FileShareCommandService {
    String createFileShare(MemberSessionDto memberSessionDto, FileShareCreateRequest fileShareCreateRequest);
    void deleteFileShare(MemberSessionDto memberSessionDto, Long fileShareId);
}
