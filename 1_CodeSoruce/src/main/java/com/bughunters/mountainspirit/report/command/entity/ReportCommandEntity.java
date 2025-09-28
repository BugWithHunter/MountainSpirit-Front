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
    private Long id;

    private LocalDateTime reportDate;

    private String reportType;

    private String adminId;

    private String reason;

    @Column(length = 1)
    private String isAccepted = "N";

    private Long reportedId;

    private Long categoryId;

    private Long reportId;

    private Long crewPostId;

    private Long replyId;

    private Long postId;

    private int suspensionCycle;
}
