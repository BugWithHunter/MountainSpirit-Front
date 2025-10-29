package com.bughunters.mountainspirit.report.query.dto;

import lombok.Data;

@Data
public class BlacklistByIdDTO {
    private Long blacklistId;
    private Long memberId;
    private String memName;
    private Long crewId;
    private Long climbCnt;
    private Integer score;
    private Long reportCount;
}
