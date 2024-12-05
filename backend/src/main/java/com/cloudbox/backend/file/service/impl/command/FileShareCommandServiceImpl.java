package com.cloudbox.backend.file.service.impl.command;

import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.file.domain.File;
import com.cloudbox.backend.file.domain.FileShare;
import com.cloudbox.backend.file.dto.request.FileShareCreateRequest;
import com.cloudbox.backend.file.repository.FileShareRepository;
import com.cloudbox.backend.file.service.interfaces.command.FileShareCommandService;
import com.cloudbox.backend.file.service.interfaces.query.FileQueryService;
import com.cloudbox.backend.file.service.interfaces.query.FileShareQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FileShareCommandServiceImpl implements FileShareCommandService {

    private final FileQueryService fileQueryService;
    private final FileShareQueryService fileShareQueryService;
    private final FileShareRepository fileShareRepository;

    @Override
    public String createFileShare(MemberSessionDto memberSessionDto, FileShareCreateRequest fileShareCreateRequest) {
        File file = fileQueryService.getFileEntityByIdAndCreateBy(memberSessionDto, fileShareCreateRequest.getFileId());

        FileShare fileShare = FileShare.createFileShare(file, UUID.randomUUID().toString(), LocalDateTime.now().plusHours(fileShareCreateRequest.getShareExpiration().getHours()));

        FileShare savedFileShare = fileShareRepository.save(fileShare);

        return savedFileShare.getUuid();
    }

    @Override
    public void deleteFileShare(MemberSessionDto memberSessionDto, Long fileShareId) {
        FileShare fileShare = fileShareQueryService.getFileShareEntityByIdAndCreateBy(memberSessionDto, fileShareId);

        fileShareRepository.delete(fileShare);
    }
}
