package com.cloudbox.backend.file.service.impl.command;

import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.file.domain.File;
import com.cloudbox.backend.file.domain.Folder;
import com.cloudbox.backend.file.exception.FolderNotFoundException;
import com.cloudbox.backend.file.repository.FileRepository;
import com.cloudbox.backend.file.repository.FolderRepository;
import com.cloudbox.backend.file.service.interfaces.command.FileCommandService;
import com.cloudbox.backend.member.domain.Member;
import com.cloudbox.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FileCommandServiceImpl implements FileCommandService {
    private final FolderRepository folderRepository;
    private final FileRepository fileRepository;
    private final MemberService memberService;

    @Override
    public Long createFile(MemberSessionDto memberSessionDto, String fileName, String realFilePath, Long folderId) {
        Member member = memberService.getMemberEntityByUsername(memberSessionDto.getUsername());
        Folder folder = folderRepository.findById(folderId).orElseThrow(FolderNotFoundException::new);

        File file = File.createFile(member, fileName, realFilePath, folder);

        File savedFile = fileRepository.save(file);

        return savedFile.getId();
    }
}
