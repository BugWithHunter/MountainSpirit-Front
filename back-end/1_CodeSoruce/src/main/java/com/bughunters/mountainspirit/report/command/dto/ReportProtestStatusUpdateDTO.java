package com.bughunters.mountainspirit.report.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReportProtestStatusUpdateDTO {
    private String status;
    private String changeStatusReason;
}
