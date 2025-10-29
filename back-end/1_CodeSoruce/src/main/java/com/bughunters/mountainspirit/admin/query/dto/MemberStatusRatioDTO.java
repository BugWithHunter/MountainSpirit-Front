package com.bughunters.mountainspirit.admin.query.dto;

import lombok.Data;

@Data
public class MemberStatusRatioDTO {
    private String status;
    private Integer count;
    private Double ratio;
}
