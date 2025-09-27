package com.bughunters.mountainspirit.member.command.repository;

import com.bughunters.mountainspirit.member.command.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
