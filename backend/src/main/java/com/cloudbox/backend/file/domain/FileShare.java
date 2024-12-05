package com.cloudbox.backend.file.domain;

import com.cloudbox.backend.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileShare extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;

    private String uuid;

    private LocalDateTime expirationDate;

    private FileShare(File file, String uuid, LocalDateTime expirationDate) {
        this.file = file;
        this.uuid = uuid;
        this.expirationDate = expirationDate;
    }

    public static FileShare createFileShare(File file, String uuid, LocalDateTime expirationDate) {
        return new FileShare(file, uuid, expirationDate);
    }
}
