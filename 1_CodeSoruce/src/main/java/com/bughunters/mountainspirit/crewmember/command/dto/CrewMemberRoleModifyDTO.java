package com.bughunters.mountainspirit.crewmember.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewMemberRoleModifyDTO {
    private Long id;            // 변경 될 크루 구성원 id
    private Long crewRoleId;    // 변경 될 등급 id
}
