package com.bughunters.mountainspirit.report.query.dto;

import lombok.Data;

@Data
public class AppealDetailByIdDTO {
    private String isAccepted;
    private String appealReason;
    private String title;
    private String content;

}
