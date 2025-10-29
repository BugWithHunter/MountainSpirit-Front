package com.bughunters.mountainspirit.crew.command.dto;


import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewRegistDTO {
    private String crewName;
    private LocalDateTime crewCreateDate;
    private String crewIntro;
    private long crewRankGauge;
    private Long cumId;   // 크루 생성될때 크루장을 CrewMember 테이블에 등록 + 회원 테이블에 크루 삽입해야됨
}
