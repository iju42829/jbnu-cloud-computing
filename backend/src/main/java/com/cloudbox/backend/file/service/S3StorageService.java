package com.cloudbox.backend.file.service;

import com.cloudbox.backend.common.dto.MemberSessionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class S3StorageService {

    private final S3Client s3Client;
    private final FileService fileService;
    private final FolderService folderService;

    @Value("${aws.bucket-name}")
    private String bucketName;
    
    public Long fileUpload(MemberSessionDto memberSessionDto, MultipartFile uploadFile, Long folderId) {
        String fullFolderPath = folderService.getFullFolderPathById(folderId);

        String savedRealFilePath = fullFolderPath + uploadFile.getOriginalFilename();

        Long savedFileId = fileService.createFile(memberSessionDto, uploadFile.getOriginalFilename(), savedRealFilePath, folderId);

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(savedRealFilePath)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(uploadFile.getBytes()));

            log.debug("파일 업로드 성공: fileId={}", savedFileId);

            return savedFileId;

        } catch (IOException e) {
            throw new RuntimeException("Error reading file data", e);
        }
    }
}
