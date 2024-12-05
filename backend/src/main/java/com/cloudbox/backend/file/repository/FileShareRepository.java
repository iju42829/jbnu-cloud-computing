package com.cloudbox.backend.file.repository;

import com.cloudbox.backend.file.domain.FileShare;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileShareRepository extends JpaRepository<FileShare, Long> {
    boolean existsByFileIdAndUuid(Long fileId, String uuid);
}
