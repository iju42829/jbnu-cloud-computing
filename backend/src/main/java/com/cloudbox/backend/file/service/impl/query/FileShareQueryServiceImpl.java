package com.cloudbox.backend.file.service.impl.query;

import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.file.domain.FileShare;
import com.cloudbox.backend.file.dto.response.FileShareResponse;
import com.cloudbox.backend.file.dto.response.FileShareResponseList;
import com.cloudbox.backend.file.exception.FileNotFoundException;
import com.cloudbox.backend.file.repository.FileShareRepository;
import com.cloudbox.backend.file.service.interfaces.query.FileShareQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileShareQueryServiceImpl implements FileShareQueryService {

    private final FileShareRepository fileShareRepository;

    @Override
    public boolean validateFileShare(Long fileId, String uuid) {
        boolean validate = fileShareRepository.existsByFileIdAndUuid(fileId, uuid);

        if (!validate)
            throw new FileNotFoundException("해당 파일에 접근할 수 없습니다.");

        return validate;
    }

    @Override
    public FileShareResponseList getSharedFiles(MemberSessionDto memberSessionDto) {
        List<FileShare> fileShareList = fileShareRepository.findAllByCreateBy(memberSessionDto.getUsername());

        return new FileShareResponseList(fileShareList.stream()
                .map(FileShareResponse::fromFileShare)
                .toList());
    }
}
