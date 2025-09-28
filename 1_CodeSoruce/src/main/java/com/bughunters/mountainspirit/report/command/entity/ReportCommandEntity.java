package com.bughunters.mountainspirit.report.command.entity;

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
    private String adminId;

    @Column(name = "reason")
    private String reason;

    @Column(length = 1, name = "isAccepted")
    private String isAccepted = "N";

    @Column(name = "reportedId")
    private Long reportedId;

    @Column(name = "categoryId")
    private Long categoryId;

    @Column(name = "reportId")
    private Long reportId;

    @Column(name = "crewPostId")
    private Long crewPostId;

    @Column(name = "replyId")
    private Long replyId;

    @Column(name = "postId")
    private Long postId;

    @Column(name = "suspension_cycle")
    private int suspensionCycle;
}
