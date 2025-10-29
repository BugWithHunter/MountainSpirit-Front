package com.bughunters.mountainspirit.climbhistory.query.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseRecordMonthlyDTO {
    private Long cumId;
    private List<String> xAxis;
    private List<Integer> yAxis;
}
