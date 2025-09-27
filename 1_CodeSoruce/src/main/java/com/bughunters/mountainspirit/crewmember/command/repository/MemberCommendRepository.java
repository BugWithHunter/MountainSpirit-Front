package com.bughunters.mountainspirit.crewmember.command.repository;

import com.bughunters.mountainspirit.crewmember.command.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCommendRepository extends JpaRepository<Member,Long> {
}
