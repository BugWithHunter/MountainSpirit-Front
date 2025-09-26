package com.bughunters.mountainspirit.mountain.command.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "Course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Course {
    @Id
    @Column(name = "poiId")
    private String poiId;

    @Column(name = "createDate")
    private LocalDateTime createDate;

    @Column(name = "lat")
    private double lat;

    @Column(name = "lot")
    private double lot;

    @Column(name = "aslAltide")
    private double aslAltide;

    @Column(name = "placeNm")
    private String placeNm;

    @Column(name = "dscrtCn")
    private String dscrtCn;

    @Column(name = "orgnExmnnPrgrsDrcntNm")
    private String orgnExmnnPrgrsDrcntNm;

    @Column(name = "sgnpstDstn1Nm")
    private String sgnpstDstn1Nm;

    @Column(name = "orgnSgnpstDstn1DrcntNm")
    private String orgnSgnpstDstn1DrcntNm;

    @Column(name = "sgnpstDstn2Nm")
    private String sgnpstDstn2Nm;

    @Column(name = "orgnSgnpstDstn2DrcntNm")
    private String orgnSgnpstDstn2DrcntNm;

    @Column(name = "sgnpstDstn3Nm")
    private String sgnpstDstn3Nm;

    @Column(name = "orgnSgnpstDstn3DrcntNm")
    private String orgnSgnpstDstn3DrcntNm;

    @Column(name = "sgnpstDstn4Nm")
    private String sgnpstDstn4Nm;

    @Column(name = "orgnSgnpstDstn4DrcntNm")
    private String orgnSgnpstDstn4DrcntNm;

    @Column(name = "orgnSgnpstDstn1DrcntCd")
    private String orgnSgnpstDstn1DrcntCd;

    @Column(name = "orgnSgnpstDstn2DrcntCd")
    private String orgnSgnpstDstn2DrcntCd;

    @Column(name = "orgnSgnpstDstn3DrcntCd")
    private String orgnSgnpstDstn3DrcntCd;

    @Column(name = "orgnSgnpstDstn4DrcntCd")
    private String orgnSgnpstDstn4DrcntCd;

    @Column(name = "crtrDt")
    private LocalDateTime crtrDt;

    @Column(name = "stampPath")
    private String stampPath;

    @Column(name = "stampUpdateDate")
    private LocalDateTime stampUpdateDate;

    @Column(name = "orgnPlaceTpeCd")
    private String orgnPlaceTpeCd;

    @Column(name = "frtrlId")
    private String frtrlId;

    @Column(name = "rankId" , nullable = true)
    private Long rankId;
}
