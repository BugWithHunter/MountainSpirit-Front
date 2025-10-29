package com.bughunters.mountainspirit.crewclimbboard.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewClimbRecordRegistDTO {
    private Long crewClimbId;
    private Long crewMemberId;
    private String frtrlId;
}
