package com.bughunters.mountainspirit.report.command.entity;

import com.bughunters.mountainspirit.board.command.entity.Board;
import com.bughunters.mountainspirit.crewboard.command.entity.CrewBoard;
import com.bughunters.mountainspirit.postcomment.command.entity.Comment;
import com.bughunters.mountainspirit.report.command.dto.ReportIsAccepted;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportCommandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "reportDate")
    private LocalDateTime reportDate;

    @Column(name = "reportType")
    private String reportType;

    @Column(name = "adminId")
    private Long adminId;

    @Column(name = "reason")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(length = 1, name = "isAccepted")
    private ReportIsAccepted isAccepted = ReportIsAccepted.U;

    @Column(name = "reportedId")
    private Long reportedId;

    @Column(name = "categoryId")
    private Long categoryId;

    @Column(name = "reportId")
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crewPostId")
    private CrewBoard crewPostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "replyId")
    private Comment replyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Board postId;

    @Column(name = "suspension_cycle")
    private int suspensionCycle;
}
