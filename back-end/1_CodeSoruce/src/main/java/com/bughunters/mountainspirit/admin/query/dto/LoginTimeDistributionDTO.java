package com.bughunters.mountainspirit.admin.query.dto;

import lombok.Data;

@Data
public class LoginTimeDistributionDTO {
    private String loginTimeRange;
    private Integer loginCount;
}
