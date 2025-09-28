package com.bughunters.mountainspirit.report.command.repository;

import com.bughunters.mountainspirit.report.command.entity.ReportCommandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportCommandRepository extends JpaRepository<ReportCommandEntity, Long> {
    long countByReportedIdAndSuspensionCycleAndCategoryId(Long reportedId, int suspensionCycle, Long categoryId);

    List<ReportCommandEntity> findByReportedIdAndSuspensionCycle(Long reportedId, int suspensionCycle);
}
