package com.bughunters.mountainspirit.admin.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginInfoStatsDTO {
    private List<LoginStatsDTO> loginStats;
    private List<LoginTimeDistributionDTO> loginTimeDistribution;
    private List<MemberStatusRatioDTO> memberStatusRatio;
}
