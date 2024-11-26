package com.cloudbox.backend.file.service.impl.query;

import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.file.domain.File;
import com.cloudbox.backend.file.dto.response.FileDownloadResponse;
import com.cloudbox.backend.file.service.interfaces.query.FileQueryService;
import com.cloudbox.backend.file.service.interfaces.query.S3StorageQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class S3StorageQueryServiceImpl implements S3StorageQueryService {

    private final S3Client s3Client;
    private final FileQueryService fileQueryService;

    @Value("${aws.bucket-name}")
    private String bucketName;

    @Override
    public FileDownloadResponse downloadFile(MemberSessionDto memberSessionDto, Long fileId) {
        File file = fileQueryService.getFileEntityByIdAndCreateBy(memberSessionDto ,fileId);

        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(file.getFilePath())
                    .build();

            ResponseInputStream<GetObjectResponse> s3Object = s3Client.getObject(getObjectRequest);

            return new FileDownloadResponse(new InputStreamResource(s3Object), file.getFileName());

        } catch (S3Exception e) {
            throw new RuntimeException("다운로드 중 에러가 발생했습니다.", e);
        }
    }
}
