package com.cloudbox.backend.file.service.impl.command;

import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.file.domain.Folder;
import com.cloudbox.backend.file.dto.request.FolderCreateRequest;
import com.cloudbox.backend.file.exception.FolderNotFoundException;
import com.cloudbox.backend.file.repository.FolderRepository;
import com.cloudbox.backend.file.service.interfaces.command.FolderCommandService;
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
public class FolderCommandServiceImpl implements FolderCommandService {

    private final FolderRepository folderRepository;
    private final MemberService memberService;

    public Long createFolder(Long parentFolderId, FolderCreateRequest folderCreateRequest, MemberSessionDto memberSessionDto) {
        Member member = memberService.getMemberEntityByUsername(memberSessionDto.getUsername());

        Folder parentFolder = folderRepository.findById(parentFolderId).orElseThrow(FolderNotFoundException::new);

        Folder folder = Folder.createFolder(folderCreateRequest.getName(), parentFolder, member);

        Folder savedFolder = folderRepository.save(folder);

        log.debug("폴더 생성 성공: folderCreator={}, folderId={}, folderType={}", savedFolder.getMember().getUsername(), savedFolder.getId(), savedFolder.getFolderType());

        return savedFolder.getId();
    }

    public Long createRootFolder(Long memberId, FolderCreateRequest folderCreateRequest) {
        Member member = memberService.getMemberEntityById(memberId);

        Folder folder = Folder.createRootFolder(folderCreateRequest.getName(), member);

        Folder savedRootFolder = folderRepository.save(folder);

        log.debug("루트 폴더 생성 성공: folderId={}, folderType={}", savedRootFolder.getId(), savedRootFolder.getFolderType());

        return savedRootFolder.getId();
    }
}
