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
    private Long crewClimbId;
    private Long crewMemberId;
    private String frtrlId;
}
