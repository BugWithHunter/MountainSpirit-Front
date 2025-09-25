package com.bughunters.mountainspirit.member.query.dto;

import lombok.*;

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
