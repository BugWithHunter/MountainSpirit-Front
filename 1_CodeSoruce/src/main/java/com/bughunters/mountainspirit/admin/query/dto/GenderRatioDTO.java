package com.bughunters.mountainspirit.admin.query.dto;

import lombok.Data;

@Data
public class GenderRatioDTO {
    private String gender;
    private Integer count;
    private Double ratio;
}
