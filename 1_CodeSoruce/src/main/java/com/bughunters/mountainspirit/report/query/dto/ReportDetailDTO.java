package com.bughunters.mountainspirit.report.query.dto;

import lombok.Data;

@Data
public class ReportDetailDTO {
    private String reason;
    private String title;   // crew/board만 존재
    private String content; // 모든 타입에서 존재
}
