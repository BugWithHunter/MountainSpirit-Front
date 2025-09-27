package com.bughunters.mountainspirit.climbhistory.command.dto;

import java.time.LocalDateTime;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FindClimbCheckDTO {
    private LocalDateTime updateTime;
    private LocalDateTime endTime;
    private String frtrlNm;
    private String placeNm;
    private String stateCode;
}
