package com.bughunters.mountainspirit.crewmember.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewApplyDTO {
    private Long id;
    private String crewApplyDate;
    private Long crewId;
    private Long cumId;
}
