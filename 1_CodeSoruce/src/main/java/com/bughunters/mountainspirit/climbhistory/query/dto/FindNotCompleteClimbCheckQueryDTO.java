package com.bughunters.mountainspirit.climbhistory.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FindNotCompleteClimbCheckQueryDTO {
    private String memberName;
    private LocalDateTime updateTime;
    private LocalDateTime endTime;
    private String frtrlNm;
    private String frtrlId;
    private String placeNm;
    private String poiId;
    private String stateCode;
    private long id;
}
