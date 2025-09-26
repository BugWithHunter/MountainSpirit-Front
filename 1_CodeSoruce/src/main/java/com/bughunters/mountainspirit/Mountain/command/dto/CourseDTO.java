package com.bughunters.mountainspirit.mountain.command.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CourseDTO {
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
    private LocalDateTime crtrDt;
    private String stampPath;
    private LocalDateTime stampUpdateDate;
    private String orgnPlaceTpeCd;
    private String frtrlId;
    private Long rankId;
}
