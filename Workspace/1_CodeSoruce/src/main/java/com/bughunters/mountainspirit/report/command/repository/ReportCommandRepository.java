package com.bughunters.mountainspirit.report.command.repository;

import com.bughunters.mountainspirit.board.command.entity.Board;
import com.bughunters.mountainspirit.crewboard.command.entity.CrewBoard;
import com.bughunters.mountainspirit.postcomment.command.entity.Comment;
import com.bughunters.mountainspirit.report.command.dto.ReportIsAccepted;
import com.bughunters.mountainspirit.report.command.entity.ReportCommandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ReportCommandRepository extends JpaRepository<ReportCommandEntity, Long> {

    long countByReportedIdAndSuspensionCycleAndCategoryIdAndIsAccepted(Long reportedId, int suspensionCycle, Long categoryId, ReportIsAccepted reportIsAccepted);

    boolean existsByPostIdAndIsAcceptedNot(Board board, ReportIsAccepted reportIsAccepted);

    boolean existsByCrewPostIdAndIsAcceptedNot(CrewBoard crewBoard, ReportIsAccepted reportIsAccepted);

    boolean existsByReplyIdAndIsAcceptedNot(Comment comment, ReportIsAccepted reportIsAccepted);

    boolean existsByIdAndReportedId(Long reportId, Long reportedId);

    @Modifying
    @Query("UPDATE ReportCommandEntity r set r.isAccepted = :status WHERE r.id = :reportId")
    void updateReportStatusByReportId(@Param("reportId") Long reportId, @Param("status") ReportIsAccepted status);
}
