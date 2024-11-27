package com.cloudbox.backend.file.service.impl.command;

import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.file.domain.File;
import com.cloudbox.backend.file.domain.Folder;
import com.cloudbox.backend.file.repository.FileRepository;
import com.cloudbox.backend.file.service.interfaces.command.FileCommandService;
import com.cloudbox.backend.file.service.interfaces.command.S3StorageCommandService;
import com.cloudbox.backend.file.service.interfaces.query.FileQueryService;
import com.cloudbox.backend.file.service.interfaces.query.FolderQueryService;
import com.cloudbox.backend.member.domain.Member;
import com.cloudbox.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FileCommandServiceImpl implements FileCommandService {
    private final FileRepository fileRepository;

    private final MemberService memberService;
    private final FolderQueryService folderQueryService;
    private final FileQueryService fileQueryService;
    private final S3StorageCommandService s3StorageCommandService;

    @Override
    public Long createFile(MemberSessionDto memberSessionDto, MultipartFile uploadFile, Long folderId) {
        Member member = memberService.getMemberEntityByUsername(memberSessionDto.getUsername());
        Folder folder = folderQueryService.getFolderEntityByIdAndCreateBy(folderId, memberSessionDto);

        String realFilePath = memberSessionDto.getUsername() + "/" + UUID.randomUUID().toString() + uploadFile.getOriginalFilename();

        String fileName = uploadFile.getOriginalFilename();

        if (fileRepository.existsByFileNameAndFolder(fileName, folder)) {
            fileName = generateUniqueFileName(fileName, folder);
        }

        File file = File.createFile(member, fileName, realFilePath, folder, uploadFile.getSize());

        File savedFile = fileRepository.save(file);

        s3StorageCommandService.fileUpload(memberSessionDto, uploadFile, realFilePath);

        return savedFile.getId();
    }


    @Override
    public void deleteFile(MemberSessionDto memberSessionDto, Long fileId) {
        File file = fileQueryService.getFileEntityByIdAndCreateBy(memberSessionDto, fileId);

        fileRepository.delete(file);
    }

    @Override
    public void moveFile(MemberSessionDto memberSessionDto, Long fileId, Long targetFolderId) {
        File file = fileQueryService.getFileEntityByIdAndCreateBy(memberSessionDto, fileId);

        Folder targetFolder = folderQueryService.getFolderEntityByIdAndCreateBy(targetFolderId, memberSessionDto);

        if (fileRepository.existsByFileNameAndFolder(file.getFileName(), targetFolder)) {
            String uniqueFileName = generateUniqueFileName(file.getFileName(), targetFolder);
            file.changeFileName(uniqueFileName);
        }

        file.changerFolder(targetFolder);
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
