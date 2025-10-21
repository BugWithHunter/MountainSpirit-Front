package com.bughunters.mountainspirit.admin.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoStatsDTO {
    private int totalMembers;                              // 전체 회원 수
    private List<Map<String, Object>> genderRatio;         // 성별 비율
    private List<Map<String, Object>> ageDistribution;    // 연령별 회원 분포
    private List<Map<String, Object>> monthlyNewMembers;  // 최근 1년 월별 신규 가입자
}
