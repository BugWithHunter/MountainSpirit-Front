package com.bughunters.mountainspirit.report.command.repository;

import com.bughunters.mountainspirit.report.command.entity.ReportMemberCommandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportMemberCommandRepository extends JpaRepository<ReportMemberCommandEntity, Long> {
}
