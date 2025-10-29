package com.bughunters.mountainspirit.climbhistory.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestSubmmitClimbMountainDTO {
    private long cumId;
    private String frtrlId;
    private String poiId;
    private String stateCode;
}
