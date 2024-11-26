package com.cloudbox.backend.file.domain;

import com.cloudbox.backend.file.config.ApplicationContextProvider;
import com.cloudbox.backend.file.service.interfaces.command.S3StorageCommandService;
import jakarta.persistence.PreRemove;

public class FileEntityListener {

    private S3StorageCommandService getS3StorageCommandService() {
        return ApplicationContextProvider
                .getApplicationContext()
                .getBean(S3StorageCommandService.class);
    }

    @PreRemove
    public void preRemove(File file) {
        getS3StorageCommandService().deleteFile(file);
    }
}
