package com.bughunters.mountainspirit.memberrank.query.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HigherClimber {
    private Long cumId;
    private String frtrlId;
    private Long mtRankId;
    private Long climbCount;
}
