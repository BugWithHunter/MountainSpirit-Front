package com.bughunters.mountainspirit.Mountain.command.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseApiPeakDTO {
    private String frtrlId;
    private String placeNm;
    private Double lot;
    private String orgnPlaceTpeCd;
    private String crtrDt;
    private Double aslAltide;
    private String dscrtCn;
    private String orgnPlaceTpeNm;
    private String poiId;
    private String frtrlNm;
    private Double lat;
}
