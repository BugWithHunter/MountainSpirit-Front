package com.bughunters.mountainspirit.report.query.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportCheckDTO {
    private long id;
    private LocalDateTime reportDate;
    private String reportType;
    private long adminId;
    private String reason;
    private String isAccepted;
    private long reportedId;
    private String reportname;
    private long reportId;
    private long crewPostId;
    private long replyId;
    private long postId;
    private int suspension_cycle;
}
