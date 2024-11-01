package com.cloudbox.backend.auth.service;

import com.cloudbox.backend.auth.domain.Member;
import com.cloudbox.backend.auth.dto.member.request.MemberCreateRequest;
import com.cloudbox.backend.auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long signUp(MemberCreateRequest memberCreateRequest) {
        Member member = Member.createMember(memberCreateRequest.getUsername(),
                passwordEncoder.encode(memberCreateRequest.getPassword()),
                memberCreateRequest.getEmail());

        Member savedId = memberRepository.save(member);

        return savedId.getId();
    }
}
