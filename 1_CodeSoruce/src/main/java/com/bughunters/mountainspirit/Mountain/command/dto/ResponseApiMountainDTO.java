package com.bughunters.mountainspirit.Mountain.command.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseApiMountainDTO {
    private String frtrlId;
    private String ctpvNm;
    private String crtrDt;
    private Double aslAltide;
    private String addrNm;
    private String frtrlNm;
    private String mtnCd;
    private Double lat;
    private Double lot;
}
