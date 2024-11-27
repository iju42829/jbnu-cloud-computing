package com.cloudbox.backend.file.service.impl.query;

import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.file.domain.File;
import com.cloudbox.backend.file.dto.response.FileDownloadResponse;
import com.cloudbox.backend.file.dto.response.FileResponse;
import com.cloudbox.backend.file.exception.FileNotFoundException;
import com.cloudbox.backend.file.repository.FileRepository;
import com.cloudbox.backend.file.service.interfaces.query.FileQueryService;
import com.cloudbox.backend.file.service.interfaces.query.S3StorageQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileQueryServiceImpl implements FileQueryService {

    private final FileRepository fileRepository;
    private final S3StorageQueryService s3StorageQueryService;

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

    @Override
    public List<FileResponse> getFileResponsesByFolder(Long folderId, MemberSessionDto memberSessionDto) {
        List<File> files = fileRepository.findByFolderIdAndCreateBy(folderId, memberSessionDto.getUsername());

        return files.stream()
                .map(FileResponse::fromFile)
                .toList();
    }

    @Override
    public FileDownloadResponse downloadFile(MemberSessionDto memberSessionDto, Long fileId) {
        File file = getFileEntityByIdAndCreateBy(memberSessionDto ,fileId);

        ResponseInputStream<GetObjectResponse> s3Object = s3StorageQueryService.downloadFile(file.getFilePath());

        return new FileDownloadResponse(new InputStreamResource(s3Object), file.getFileName());
    }
}
