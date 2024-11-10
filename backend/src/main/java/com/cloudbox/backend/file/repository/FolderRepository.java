package com.cloudbox.backend.file.repository;

import com.cloudbox.backend.file.domain.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folder, Long> {
}
