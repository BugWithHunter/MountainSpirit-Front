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
}
