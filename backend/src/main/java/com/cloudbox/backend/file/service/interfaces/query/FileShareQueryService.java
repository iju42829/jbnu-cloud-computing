package com.cloudbox.backend.file.service.interfaces.query;

public interface FileShareQueryService {
    boolean validateFileShare(Long fileId, String uuid);
}
