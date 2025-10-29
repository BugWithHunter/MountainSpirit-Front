package com.bughunters.mountainspirit.report.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReportProtestRequestDTO {
    private String reason;
    private Long reportedId;
    private Long reportId;
}
