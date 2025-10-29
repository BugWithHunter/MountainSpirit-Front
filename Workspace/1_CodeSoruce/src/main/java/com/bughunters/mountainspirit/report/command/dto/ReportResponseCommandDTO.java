package com.bughunters.mountainspirit.report.command.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportResponseCommandDTO {
    private Long id;
    private String reportType;
    private String reason;
    private Long reportedId;
    private Long adminId;
    private int suspensionCycle;
    private String result; // BAN, BLACKLIST, NORMAL
}
