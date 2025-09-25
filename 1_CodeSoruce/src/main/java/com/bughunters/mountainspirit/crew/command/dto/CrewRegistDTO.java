package com.bughunters.mountainspirit.crew.command.dto;


import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewRegistDTO {
    private Long id;
    private String crewName;
    private char crewIsRecruit;
    private LocalDateTime crewCreateDate;
    private Integer crewMaxPeople;
    private String crewIntro;
    private char crewIsDeleted;
//    private int crewBanDate;
    private long crewRankGauge;
}
