package com.bughunters.mountainspirit.report.command.repository;

import com.bughunters.mountainspirit.board.command.entity.Board;
import com.bughunters.mountainspirit.crewboard.command.entity.CrewBoard;
import com.bughunters.mountainspirit.postcomment.command.entity.Comment;
import com.bughunters.mountainspirit.report.command.dto.ReportIsAccepted;
import com.bughunters.mountainspirit.report.command.entity.ReportCommandEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReportCommandRepository extends JpaRepository<ReportCommandEntity, Long> {

    long countByReportedIdAndSuspensionCycleAndCategoryIdAndIsAccepted(Long reportedId, int suspensionCycle, Long categoryId, ReportIsAccepted reportIsAccepted);

    boolean existsByPostIdAndIsAcceptedNot(Board board, ReportIsAccepted reportIsAccepted);

    boolean existsByCrewPostIdAndIsAcceptedNot(CrewBoard crewBoard, ReportIsAccepted reportIsAccepted);

    boolean existsByReplyIdAndIsAcceptedNot(Comment comment, ReportIsAccepted reportIsAccepted);
}
