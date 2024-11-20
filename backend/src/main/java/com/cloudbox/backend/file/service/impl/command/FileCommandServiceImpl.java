package com.cloudbox.backend.file.service.impl.command;

import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.file.domain.File;
import com.cloudbox.backend.file.domain.Folder;
import com.cloudbox.backend.file.exception.FolderNotFoundException;
import com.cloudbox.backend.file.repository.FileRepository;
import com.cloudbox.backend.file.repository.FolderRepository;
import com.cloudbox.backend.file.service.interfaces.command.FileCommandService;
import com.cloudbox.backend.file.service.interfaces.query.FileQueryService;
import com.cloudbox.backend.file.service.interfaces.query.FolderQueryService;
import com.cloudbox.backend.member.domain.Member;
import com.cloudbox.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FileCommandServiceImpl implements FileCommandService {
    private final FileRepository fileRepository;

    private final MemberService memberService;
    private final FolderQueryService folderQueryService;
    private final FileQueryService fileQueryService;

    @Override
    public Long createFile(MemberSessionDto memberSessionDto, String fileName, String realFilePath, Long folderId) {
        Member member = memberService.getMemberEntityByUsername(memberSessionDto.getUsername());
        Folder folder = folderQueryService.getFolderEntityById(folderId);

        if (fileRepository.existsByFileNameAndFolder(fileName, folder)) {
            fileName = generateUniqueFileName(fileName, folder);
        }

        File file = File.createFile(member, fileName, realFilePath, folder);

        File savedFile = fileRepository.save(file);

        return savedFile.getId();
    }

    @Override
    public void deleteFile(MemberSessionDto memberSessionDto, Long fileId) {
        File file = fileQueryService.getFileEntityByIdAndCreateBy(memberSessionDto, fileId);

        fileRepository.delete(file);
    }

    private String generateUniqueFileName(String fileName, Folder folder) {
        // 파일명과 확장자 분리
        String extension = StringUtils.getFilenameExtension(fileName); // 확장자 추출
        String baseName = fileName;
        if (extension != null) {
            baseName = fileName.substring(0, fileName.length() - extension.length() - 1); // 확장자 제외한 파일명
        }

        String uniqueFileName = fileName;
        int counter = 1;

        // 중복 이름 체크: 동일 이름이 존재하면 "(n)" 추가
        while (fileRepository.existsByFileNameAndFolder(uniqueFileName, folder)) {
            uniqueFileName = String.format("%s (%d)%s", baseName, counter,
                    extension == null ? "" : "." + extension);
            counter++;
        }

        return uniqueFileName;
    }
}
