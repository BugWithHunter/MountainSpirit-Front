package com.bughunters.mountainspirit.admin.query.dto;

import lombok.Data;

@Data
public class CrewSummaryDTO {
    private int totalCrewCount;
    private int newCrewCount;
    private int activeCrewCount;
}
