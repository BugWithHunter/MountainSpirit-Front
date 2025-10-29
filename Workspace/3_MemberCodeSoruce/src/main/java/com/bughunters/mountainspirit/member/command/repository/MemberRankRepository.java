package com.bughunters.mountainspirit.member.command.repository;

import com.bughunters.mountainspirit.member.command.entity.MemberRank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRankRepository extends JpaRepository<MemberRank, Long> {
}
