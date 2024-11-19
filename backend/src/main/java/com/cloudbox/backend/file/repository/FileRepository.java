package com.cloudbox.backend.file.repository;

import com.cloudbox.backend.file.domain.File;
import com.cloudbox.backend.file.domain.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByFolderId(Long folderId);
    boolean existsByFileNameAndFolder(String fileName, Folder folder);
}
