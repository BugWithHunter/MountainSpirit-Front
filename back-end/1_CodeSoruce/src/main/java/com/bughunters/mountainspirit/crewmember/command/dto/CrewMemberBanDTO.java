package com.bughunters.mountainspirit.crewmember.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewMemberBanDTO {
    private Long id;            // 추방할 크루 구성원 id
    private Long crewId;
    private Long cumId;
    private String banReason;   // 추방 사유
}
