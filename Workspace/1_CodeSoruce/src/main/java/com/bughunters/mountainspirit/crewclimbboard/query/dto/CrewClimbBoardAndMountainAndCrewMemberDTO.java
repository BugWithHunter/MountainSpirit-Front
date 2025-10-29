package com.bughunters.mountainspirit.crewclimbboard.query.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewClimbBoardAndMountainAndCrewMemberDTO {
    private Long id;
    private String crewClimbStartDate;
    private String crewClimbRecruitStartDate;
    private String crewClimbRecruitEndDate;
    private String crewClimbContent;
    private char crewClimbIsDeleted;
    private Integer crewClimbAmountOfPeople;
    private char crewClimbIsEnded;
    private Long crewId;
    private MountainDTO mountain;
    private List<MemberDTO> member;
}
