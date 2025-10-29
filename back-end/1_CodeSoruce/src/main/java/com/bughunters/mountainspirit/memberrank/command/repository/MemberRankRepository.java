package com.bughunters.mountainspirit.memberrank.command.repository;

import com.bughunters.mountainspirit.memberrank.command.entity.MemberRank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRankRepository extends JpaRepository<MemberRank, Long> {
}
