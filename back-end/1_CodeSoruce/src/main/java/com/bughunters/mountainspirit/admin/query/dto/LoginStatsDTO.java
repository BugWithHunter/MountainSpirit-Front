package com.bughunters.mountainspirit.admin.query.dto;

import lombok.Data;

@Data
public class LoginStatsDTO {
    private Integer login7d;
    private Integer login30d;
    private Double recent7Ratio;
    private Double recent30Ratio;
    private Double loyaltyRate;
}
