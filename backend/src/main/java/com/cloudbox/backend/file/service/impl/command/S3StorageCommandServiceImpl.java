package com.cloudbox.backend.file.service.impl.command;

import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.file.domain.File;
import com.cloudbox.backend.file.service.interfaces.command.S3StorageCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class S3StorageCommandServiceImpl implements S3StorageCommandService {

    private final S3Client s3Client;

    @Value("${aws.bucket-name}")
    private String bucketName;

    @Override
    public void deleteFile(File file) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(file.getFilePath())
                .build();

            s3Client.deleteObject(deleteObjectRequest);
        }

    @Override
    public void fileUpload(MemberSessionDto memberSessionDto, MultipartFile uploadFile, String realFilePath) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(realFilePath)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(uploadFile.getBytes()));

            log.debug("파일 업로드 성공");

        } catch (IOException e) {
            throw new RuntimeException("Error reading file data", e);
        }
    }
}