package com.bughunters.mountainspirit.climbhistory.query.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestStartClimbMountainQueryDTO {
    private long cumId;
    private String frtrlId;
    private String poiId;
    private String stateCode;
}
