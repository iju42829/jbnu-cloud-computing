package com.cloudbox.backend.file.domain;

import com.cloudbox.backend.common.domain.BaseEntity;
import com.cloudbox.backend.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, updatable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;

    private String fileName;

    private String filePath;

    private File(Member member, String fileName, String filePath, Folder folder) {
        this.member = member;
        this.fileName = fileName;
        this.filePath = filePath;
        this.folder = folder;
    }

    public static File createFile(Member member, String fileName, String filePath, Folder folder) {
        return new File(member, fileName, filePath, folder);
    }
}
