package com.cloudbox.backend.file.repository;

import com.cloudbox.backend.file.domain.FileShare;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileShareRepository extends JpaRepository<FileShare, Long> {
    boolean existsByFileIdAndUuid(Long fileId, String uuid);

    @EntityGraph(attributePaths = {"file"})
    List<FileShare> findAllByCreateBy(String createBy);

    Optional<FileShare> findByIdAndCreateBy(Long fileShareId, String createBy);
}
