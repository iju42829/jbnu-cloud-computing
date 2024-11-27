package com.cloudbox.backend.file.service.interfaces.query;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;


public interface S3StorageQueryService {
    ResponseInputStream<GetObjectResponse> downloadFile(String filePath);
}