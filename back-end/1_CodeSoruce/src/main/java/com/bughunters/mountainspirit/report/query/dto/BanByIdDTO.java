package com.bughunters.mountainspirit.report.query.dto;

import lombok.Data;

@Data
public class BanByIdDTO {
    private Long banId;
    private Long userId;
    private String memName;
    private Long crewId;
    private Long climbCnt;
    private Integer score;
    private Long reportCount;
}
