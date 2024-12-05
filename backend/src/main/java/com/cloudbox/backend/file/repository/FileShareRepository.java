package com.cloudbox.backend.file.repository;

import com.cloudbox.backend.file.domain.FileShare;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FileShareRepository extends JpaRepository<FileShare, Long> {
    boolean existsByFileIdAndUuidAndExpirationDateAfter(Long fileId, String uuid, LocalDateTime currentTime);

    @EntityGraph(attributePaths = {"file"})
    List<FileShare> findAllByCreateBy(String createBy);

    Optional<FileShare> findByIdAndCreateBy(Long fileShareId, String createBy);

    int deleteByExpirationDateBefore(LocalDateTime currentTime);
}
