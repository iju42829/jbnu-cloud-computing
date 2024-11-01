package com.cloudbox.backend.auth.repository;

import com.cloudbox.backend.auth.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
