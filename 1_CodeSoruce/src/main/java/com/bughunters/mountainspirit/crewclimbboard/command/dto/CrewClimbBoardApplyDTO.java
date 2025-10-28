package com.bughunters.mountainspirit.crewclimbboard.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewClimbBoardApplyDTO {
    private Long crewClimbId;
    private Long cumId;
    private String frtrlId;
    private Integer crewClimbAmountOfPeople;
}
