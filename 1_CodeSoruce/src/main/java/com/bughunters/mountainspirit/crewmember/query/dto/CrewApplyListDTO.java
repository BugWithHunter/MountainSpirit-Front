package com.bughunters.mountainspirit.crewmember.query.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewApplyListDTO {
    private Long crewId;
    List<AppliedMemberInfoDTO> appliedList;
}
