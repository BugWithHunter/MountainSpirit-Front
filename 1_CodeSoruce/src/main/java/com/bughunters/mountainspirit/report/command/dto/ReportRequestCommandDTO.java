package com.bughunters.mountainspirit.report.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReportRequestCommandDTO {
    private Long id;
    private String reportType;
    private String reason;
    private Long reportedId;
    private Long categoryId;
    private Long reportId;
}
