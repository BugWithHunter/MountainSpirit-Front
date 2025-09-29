package com.bughunters.mountainspirit.report.command.repository;

import com.bughunters.mountainspirit.report.command.dto.ReportIsAccepted;
import com.bughunters.mountainspirit.report.command.entity.ReportCommandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReportCommandRepository extends JpaRepository<ReportCommandEntity, Long> {
    long countByReportedIdAndSuspensionCycleAndCategoryId(Long reportedId, int suspensionCycle, Long categoryId);

    List<ReportCommandEntity> findByReportedIdAndSuspensionCycle(Long reportedId, int suspensionCycle);

    @Transactional
    @Modifying
    @Query("UPDATE ReportCommandEntity r SET r.isAccepted = :status WHERE r.id = :id")
    int updateIsAcceptedById(Long id, ReportIsAccepted status);


    long countByReportedIdAndSuspensionCycleAndCategoryIdAndIsAccepted(Long reportedId, int suspensionCycle, Long categoryId, ReportIsAccepted reportIsAccepted);
}
