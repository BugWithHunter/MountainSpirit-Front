package com.bughunters.mountainspirit.report.command.repository;


import com.bughunters.mountainspirit.member.command.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportMemberCommandRepository extends JpaRepository<Member, Long> {
}
