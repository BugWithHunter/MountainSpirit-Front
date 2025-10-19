package com.bughunters.mountainspirit.report.command.repository;

import com.bughunters.mountainspirit.report.command.entity.ReportProtestCommandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportProtestCommandRepository extends JpaRepository<ReportProtestCommandEntity, Long> {

    @Modifying
    @Query("UPDATE ReportCommandEntity r set r.isAccepted = :status WHERE r.id = :reportId")
    void updateReportStatusByReportId(@Param("reportId") Long reportId,@Param("status") String status);
}
