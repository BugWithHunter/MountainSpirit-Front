package com.bughunters.mountainspirit.admin.query.dto;

import lombok.Data;

@Data
public class CourseDifficultyStateDTO {
    private Integer difficulty;
    private Integer courseCount;
    private Double percentage;
    private Double avgAsl;
}
