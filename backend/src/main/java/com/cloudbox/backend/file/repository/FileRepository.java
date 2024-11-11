package com.cloudbox.backend.file.repository;

import com.cloudbox.backend.file.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {

}
