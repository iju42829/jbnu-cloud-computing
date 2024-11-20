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

    @Override
    public String getFilePathById(MemberSessionDto memberSessionDto, Long fileId) {
        File file = fileRepository
                .findByIdAndCreateBy(fileId, memberSessionDto.getUsername())
                .orElseThrow(FileNotFoundException::new);

        return file.getFilePath();
    }

    @Override
    public File getFileEntityByIdAndCreateBy(MemberSessionDto memberSessionDto, Long fileId) {
        return fileRepository
                .findByIdAndCreateBy(fileId, memberSessionDto.getUsername())
                .orElseThrow(FileNotFoundException::new);
    }

    public List<FileResponse> getFileResponsesByFolder(Long folderId, MemberSessionDto memberSessionDto) {
        List<File> files = fileRepository.findByFolderIdAndCreateBy(folderId, memberSessionDto.getUsername());

        return files.stream()
                .map(FileResponse::fromFile)
                .toList();
    }
}
