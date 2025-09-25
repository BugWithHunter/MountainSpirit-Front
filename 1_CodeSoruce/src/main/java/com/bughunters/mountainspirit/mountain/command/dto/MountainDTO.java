package com.bughunters.mountainspirit.mountain.command.dto;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MountainDTO {
    private String frtrlId;
    private String frtrlNm;
    private String ctpvNm;
    private String addrNm;
    private double lat;
    private double lot;
    private java.sql.Date crtrDt;
    private int mtnCd;
    private String stampPath;
    private java.sql.Date stampUpdateDate;
}
