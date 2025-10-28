package com.bughunters.mountainspirit.climbhistory.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MountainRankDTO {
    private Long cumId;
    private String memName;
    private String frtrlId;
    private Long mtRankId;
    private String rankName;
    private int count;
}
