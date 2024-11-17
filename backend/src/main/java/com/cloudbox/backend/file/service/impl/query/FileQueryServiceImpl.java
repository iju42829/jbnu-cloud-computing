package com.cloudbox.backend.file.service.impl.query;

import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.file.domain.File;
import com.cloudbox.backend.file.dto.response.FileResponse;
import com.cloudbox.backend.file.exception.FileNotFoundException;
import com.cloudbox.backend.file.repository.FileRepository;
import com.cloudbox.backend.file.service.interfaces.query.FileQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileQueryServiceImpl implements FileQueryService {

    private final FileRepository fileRepository;

    public String getFilePathById(Long fileId) {
        File file = fileRepository
                .findById(fileId)
                .orElseThrow(FileNotFoundException::new);

        return file.getFilePath();
    }

    public List<FileResponse> getFileResponsesByFolder(Long folderId, MemberSessionDto memberSessionDto) {
        List<File> files = fileRepository.findByFolderId(folderId);

        return files.stream()
                .map(FileResponse::fromFile)
                .toList();
    }
}
