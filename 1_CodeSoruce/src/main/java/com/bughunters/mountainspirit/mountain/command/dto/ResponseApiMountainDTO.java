package com.bughunters.mountainspirit.mountain.command.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseApiMountainDTO {
    private String frtrlId;
    private String frtrlNm;
    private String ctpvNm;
    private String addrNm;
    private double lat;
    private double lot;
    private Double aslAltide;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime crtrDt;
    private Long mtnCd;
    private String stampPath;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime stampUpdateDate;
}
