package com.bughunters.mountainspirit.crewmember.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewIdentifyMemberDTO {
    private Long crewId;
    private Long cumId;
}
