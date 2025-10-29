package com.bughunters.mountainspirit.admin.query.dto;

import lombok.Data;

@Data
public class AdminMemberAffiliationDTO {
    private Long affiliatedCount;
    private Long unaffiliatedCount;
    private Long totalMembers;
    private Double affiliatedRate;
    private Double unaffiliatedRate;
}
