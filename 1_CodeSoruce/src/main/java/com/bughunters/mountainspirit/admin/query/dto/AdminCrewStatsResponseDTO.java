package com.bughunters.mountainspirit.admin.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminCrewStatsResponseDTO {
    private CrewDetailSummaryDTO countSummary;
    private AdminMemberAffiliationDTO memberAffiliation;
    private AdminCrewMemberStateRateDTO memberStateRates;
    private AdminCrewClimbSuccessDTO climbSuccessRate;
}
