package com.bughunters.mountainspirit.admin.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrewStatsDTO {
    // 전체 크루 수
    private int totalCrewCount;

    // 이번 달 새로 생성된 크루 수
    private int newCrewCount;

    // 활동 중인 크루 수
    private int activeCrewCount;

    List<CrewMonthlyStatsDTO> monthlyCrewStats;
}
