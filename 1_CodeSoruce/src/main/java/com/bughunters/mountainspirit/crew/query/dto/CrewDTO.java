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
    private String crewName;
    private char crewIsRecruit;
    private Integer crewMaxPeople;
    private long crewRankGauge;
}
