package com.bughunters.mountainspirit.crewclimbboard.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyCrewClimbBoardListDTO {
    private Long id;
    private char crewClimbHistoryIsSucceed;
    private Long crewMemberId;
    private CrewClimbDTO crewClimbRecord;
    private MountainDTO mountain;
}
