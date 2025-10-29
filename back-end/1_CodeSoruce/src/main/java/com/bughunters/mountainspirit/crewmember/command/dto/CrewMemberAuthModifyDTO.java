package com.bughunters.mountainspirit.crewmember.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewMemberAuthModifyDTO {
    private String crewRoleOriginalName;
    private String crewRoleName;
    private String crewRoleOriginalAuth;
    private String crewRoleAuth;
}
