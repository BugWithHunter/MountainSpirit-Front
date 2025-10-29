package com.bughunters.mountainspirit.mountain.command.dto;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

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
    private LocalDateTime crtrDt;
    private long mtnCd;
    private String stampPath;
    private LocalDateTime stampUpdateDate;
}
