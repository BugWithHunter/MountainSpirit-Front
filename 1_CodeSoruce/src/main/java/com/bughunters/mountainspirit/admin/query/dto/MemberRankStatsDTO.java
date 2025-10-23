package com.bughunters.mountainspirit.admin.query.dto;

import lombok.Data;

@Data
public class MemberRankStatsDTO {
    private String levelRange;   // 씨앗, 새싹이, 나뭇잎, 꽃잎, 열매, 황금씨앗
    private Integer memberCount;     // 등급별 회원 수
    private double avgScore;     // 평균 점수
    private double avgClimbCnt;  // 평균 등산 횟수

}
