package com.bughunters.mountainspirit.crewmember.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AppliedMemberInfoDTO {
    private Long id;
    private String memId;
    private String nickname;
}
