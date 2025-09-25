package com.bughunters.mountainspirit.member.command.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseClimbMountainDTO {
    private long id;
    private String stateCode;
    private LocalDateTime updateTime;
    private LocalDateTime endTime;
    private long cumId;
    private String frtrlId;
    private String poiId;
    private String message;
}
