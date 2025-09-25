package com.bughunters.mountainspirit.mountain.command.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Mountain1")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Mountain {

    @Id
    @Column(name = "frtrlId")
    private String id;

    @Column(name = "frtrlNm")
    private String frtrlNm;

    @Column(name = "ctpvNm")
    private String ctpvNm;

    @Column(name = "addrNm")
    private String addrNm;

    @Column(name = "lat")
    private double lat;

    @Column(name = "lot")
    private double lot;

    @Column(name = "aslAltide")
    private Double aslAltide;

    @Column(name = "crtrDt")
    private LocalDateTime crtrDt;

    @Column(name = "mtnCd")
    private Long mtnCd;

    @Column(name = "stampPath")
    private String stampPath;

    @Column(name = "stampUpdateDate")
    private LocalDateTime stampUpdateDate;
}