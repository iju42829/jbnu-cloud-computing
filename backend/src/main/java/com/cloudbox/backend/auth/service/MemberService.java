package com.cloudbox.backend.auth.service;

import com.cloudbox.backend.auth.dto.member.request.MemberCreateRequest;

public interface MemberService {

    Long signUp(MemberCreateRequest memberCreateRequest);
}
