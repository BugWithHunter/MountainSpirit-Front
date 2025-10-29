package com.bughunters.mountainspirit.report.query.dto;

import lombok.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReportProtestQueryDTO {
    private Long id;
    private LocalDateTime appealDate;
    private Long adminId;
    private String appealReason;
    private String changeStatusReason;
    private String isAccepted;
    private Long reportedId;
    private Long reportId;

}
