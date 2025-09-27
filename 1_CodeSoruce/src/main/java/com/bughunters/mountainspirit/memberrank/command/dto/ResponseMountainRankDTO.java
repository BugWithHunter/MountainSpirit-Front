package com.bughunters.mountainspirit.memberrank.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseMountainRankDTO {
    private Long cumId;
    private Long mtRankId;
    private String mtRankName;
    private Long climbCount;

    private String memberType;
}
