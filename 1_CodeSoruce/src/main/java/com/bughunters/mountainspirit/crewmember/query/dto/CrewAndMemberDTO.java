package com.bughunters.mountainspirit.crewmember.query.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewAndMemberDTO {
    private long id;
    private String crewMemberJoinDate;
    private String crewMemberRoleUpdateDate;
    private long crewId;
    private long crewRoleId;
    private MemberDTO memberList;
}
