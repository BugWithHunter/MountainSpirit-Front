package com.bughunters.mountainspirit.report.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReportQueryDTO {
    private long id;
    private LocalDateTime reportDate;
    private String reportType;
    private long adminId;
    private String reason;
    private String isAccepted;
    private long reportedId;
    private long categoryId;
    private long reportId;
    private long crewPostId;
    private long replyId;
    private long postId;
    private int suspensionCycle;
}
