package com.bughunters.mountainspirit.admin.query.dto;

import lombok.Data;

@Data
public class MemberRankInfoDTO {
    private Long id;         // 회원 기본키
    private Integer climbCnt; // 등산 횟수
    private Integer score;    // 점수 (등급 산정 기준)
    private String rank;      // 회원 등급 이름
    private Long crewId;      // 크루 번호
}
