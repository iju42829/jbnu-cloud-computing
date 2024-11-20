package com.cloudbox.backend.file.repository;

import com.cloudbox.backend.file.domain.Folder;
import com.cloudbox.backend.file.dto.FolderType;
import com.cloudbox.backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    Optional<Folder> findByMemberAndFolderType(Member member, FolderType folderType);
}
