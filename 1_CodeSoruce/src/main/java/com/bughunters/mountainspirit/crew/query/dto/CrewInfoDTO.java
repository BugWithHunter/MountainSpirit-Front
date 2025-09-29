package com.bughunters.mountainspirit.crew.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewInfoDTO {
    private long id;
    private String crewName;
    private char crewIsRecruit;
    private String crewCreateDate;
    private Integer crewMaxPeople;
    private String crewIntro;
    private long crewRankGauge;
}
