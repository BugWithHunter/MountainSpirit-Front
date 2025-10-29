package com.bughunters.mountainspirit.admin.query.dto;

import lombok.Data;

@Data
public class MountainTopDTO {
    private Long mountainId;
    private String mountainName;
    private Long climbCount;
}
