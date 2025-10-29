package com.bughunters.mountainspirit.admin.query.dto;

import lombok.Data;

@Data
public class MonthlyNewMemberDTO {
    private Integer year;
    private Integer month;
    private Integer newMembers;
}
