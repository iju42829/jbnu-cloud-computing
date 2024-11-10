package com.cloudbox.backend.member.service;

import com.cloudbox.backend.common.constants.Role;
import com.cloudbox.backend.file.dto.request.FolderCreateRequest;
import com.cloudbox.backend.file.repository.FolderRepository;
import com.cloudbox.backend.file.service.FolderService;
import com.cloudbox.backend.member.domain.Member;
import com.cloudbox.backend.member.dto.request.MemberCreateRequest;
import com.cloudbox.backend.member.exception.MemberNotFoundException;
import com.cloudbox.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final FolderService folderService;
    private final FolderRepository folderRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long signUp(MemberCreateRequest memberCreateRequest) {
        Member member = Member.createMember(memberCreateRequest.getUsername(),
                passwordEncoder.encode(memberCreateRequest.getPassword()),
                memberCreateRequest.getEmail(),
                Role.USER);
        Member savedMember = memberRepository.save(member);

        log.debug("회원 가입 성공: username={}", member.getUsername());

        folderService.createRootFolder(savedMember.getId(), new FolderCreateRequest(memberCreateRequest.getUsername()));

        return savedMember.getId();
    }

    @Override
    public Member getMemberEntityByUsername(String username) {
        return memberRepository
                .findByUsername(username)
                .orElseThrow(MemberNotFoundException::new);
    }

    @Override
    public Member getMemberEntityById(Long memberId) {
        return memberRepository
                .findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }
}
