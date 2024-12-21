package com.cloudbox.backend.file.repository;

import com.cloudbox.backend.file.domain.File;
import com.cloudbox.backend.file.domain.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByFolderIdAndCreateBy(Long folderId, String createBy);
    boolean existsByFileNameAndFolder(String fileName, Folder folder);

    Optional<File> findByIdAndCreateBy(Long fileId, String createBy);

    List<File> findByFileNameContainingAndCreateBy(String name, String createBy);
}
