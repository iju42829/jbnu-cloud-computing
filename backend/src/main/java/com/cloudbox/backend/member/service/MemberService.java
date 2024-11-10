package com.cloudbox.backend.member.service;

import com.cloudbox.backend.member.domain.Member;
import com.cloudbox.backend.member.dto.request.MemberCreateRequest;

public interface MemberService {

    Long signUp(MemberCreateRequest memberCreateRequest);

    Member getMemberEntityByUsername(String username);

    Member getMemberEntityById(Long memberId);
}
