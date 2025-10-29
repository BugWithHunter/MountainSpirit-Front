package com.bughunters.mountainspirit.mountain.query.dto;

import lombok.*;

import java.time.LocalDateTime;

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
    private double aslAltide;
    private LocalDateTime crtrDt;
    private long mtnCd;
    private String stampPath;
    private LocalDateTime stampUpdateDate;
}
