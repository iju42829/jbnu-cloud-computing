package com.cloudbox.backend.file.service.impl.query;

import com.cloudbox.backend.file.service.interfaces.query.S3StorageQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${aws.bucket-name}")
    private String bucketName;

    @Override
    public ResponseInputStream<GetObjectResponse> downloadFile(String filePath) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filePath)
                    .build();

            return s3Client.getObject(getObjectRequest);

        } catch (S3Exception e) {
            throw new RuntimeException("다운로드 중 에러가 발생했습니다.", e);
        }
    }
}
