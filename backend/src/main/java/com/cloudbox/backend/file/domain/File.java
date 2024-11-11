package com.cloudbox.backend.file.domain;

import com.cloudbox.backend.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, updatable = false)
    private Member member;

    private String fileName;

    private String filePath;

    private File(Member member, String fileName, String filePath) {
        this.member = member;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public static File createFile(Member member, String fileName, String filePath) {
        return new File(member, fileName, filePath);
    }
}
