package com.cloudbox.backend.file.service;

import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.file.domain.File;
import com.cloudbox.backend.file.exception.FileNotFoundException;
import com.cloudbox.backend.file.repository.FileRepository;
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
public class FileService {
    private final FileRepository fileRepository;
    private final MemberService memberService;

    public Long createFile(MemberSessionDto memberSessionDto, String fileName, String realFilePath) {
        Member member = memberService.getMemberEntityByUsername(memberSessionDto.getUsername());

        File file = File.createFile(member, fileName, realFilePath);

        File savedFile = fileRepository.save(file);

        return savedFile.getId();
    }

    public String getFilePathById(Long fileId) {
        File file = fileRepository
                .findById(fileId)
                .orElseThrow(FileNotFoundException::new);

        return file.getFilePath();
    }
}
