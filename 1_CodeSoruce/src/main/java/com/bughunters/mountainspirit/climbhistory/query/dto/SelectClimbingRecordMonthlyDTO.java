package com.bughunters.mountainspirit.climbhistory.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SelectClimbingRecordMonthlyDTO {
    private Long cumId;
    private String monthly;
    private int count;
}
