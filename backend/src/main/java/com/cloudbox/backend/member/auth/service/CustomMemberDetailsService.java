package com.cloudbox.backend.member.auth.service;

import com.cloudbox.backend.member.auth.dto.CustomMemberDetails;
import com.cloudbox.backend.member.domain.Member;
import com.cloudbox.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new CustomMemberDetails(member);
    }
}
