package com.bughunters.mountainspirit.crew.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewDTO {
    private long id;
    private char crewIsRecruit;
    private LocalDateTime crewCreateDate;
    private Integer crewMaxPeople;
    private String crewIntro;
    private char crewIsDeleted;
    private int crewBanDate;
    private long crewRankGauge;
}
