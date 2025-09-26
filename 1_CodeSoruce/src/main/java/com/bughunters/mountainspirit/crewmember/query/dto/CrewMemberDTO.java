package com.bughunters.mountainspirit.crewmember.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewMemberDTO {
    private long id;
    private String crewMemberJoinDate;
    private String crewMemberRoleUpdateDate;
    private long crewId;
    private long cumId;
    private long crewRoleId;
}
