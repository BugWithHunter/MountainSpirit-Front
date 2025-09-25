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
public class ResponseApiPeakDTO {
    private String poiId;
    private LocalDateTime createDate;
    private double lat;
    private double lot;
    private double aslAltide;
    private String placeNm;
    private String dscrtCn;
    private String orgnExmnnPrgrsDrcntNm;
    private String sgnpstDstn1Nm;
    private String orgnSgnpstDstn1DrcntNm;
    private String sgnpstDstn2Nm;
    private String orgnSgnpstDstn2DrcntNm;
    private String sgnpstDstn3Nm;
    private String orgnSgnpstDstn3DrcntNm;
    private String sgnpstDstn4Nm;
    private String orgnSgnpstDstn4DrcntNm;
    private String orgnSgnpstDstn1DrcntCd;
    private String orgnSgnpstDstn2DrcntCd;
    private String orgnSgnpstDstn3DrcntCd;
    private String orgnSgnpstDstn4DrcntCd;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime crtrDt;
    private String stampPath;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime stampUpdateDate;
    private String orgnPlaceTpeCd;
    private String frtrlId;
    private Long rankId;
}
