package com.bughunters.mountainspirit.crew.command.dto;


import jakarta.persistence.Column;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewModifyDTO {
    private Long id;
    private String crewName;
    private char crewIsRecruit;
    private Integer crewMaxPeople;
    private String crewIntro;
    private int crewBanDate;
}
