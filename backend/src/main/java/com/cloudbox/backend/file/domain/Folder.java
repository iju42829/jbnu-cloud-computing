package com.cloudbox.backend.file.domain;

import com.cloudbox.backend.common.domain.BaseEntity;
import com.cloudbox.backend.file.dto.FolderType;
import com.cloudbox.backend.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Folder extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Folder parentFolder;

    @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Folder> childFolders = new ArrayList<>();

    @OneToMany(mappedBy = "folder", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<File> files = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private FolderType folderType;

    private Folder(String name, FolderType folderType, Folder parentFolder, Member member) {
        this.name = name;
        this.folderType = folderType;
        this.parentFolder = parentFolder;
        this.member = member;
    }

    public static Folder createRootFolder(String name, Member member) {
        return new Folder(name + "/", FolderType.ROOT, null, member);
    }

    public static Folder createFolder(String name, Folder parentFolder, Member member) {
        return new Folder(name + "/", FolderType.SUB, parentFolder, member);
    }

    public void changeParentFolder(Folder parentFolder) {
        this.parentFolder = parentFolder;
    }

    public String buildFullFolderPath() {
        if (this.parentFolder == null) {
            return this.name;
        }

        return this.parentFolder.buildFullFolderPath() + this.name;
    }
}
