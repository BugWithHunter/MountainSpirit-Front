package com.bughunters.mountainspirit.admin.query.dto;

import lombok.Data;

@Data
public class AdminCrewMemberStateRateDTO {
    private Double joinedRate;
    private Double quitedRate;
    private Double bannedRate;
    private Long totalInstances;
}
