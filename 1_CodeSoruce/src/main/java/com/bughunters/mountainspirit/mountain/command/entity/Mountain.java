package com.bughunters.mountainspirit.mountain.command.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.context.annotation.Configuration;

@Entity
@Table(name = "Mountain")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Mountain {
    @Id
    @Column(name = "frtrlId")
    private String frtrlId;

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
    private double aslAltide;

    @Column(name = "crtrDt")
    private java.util.Date crtrDt;

    @Column(name = "mtnCd")
    private long mtnCd;

    @Column(name = "stampPath")
    private String stampPath;

    @Column(name = "stampUpdateDate")
    private java.util.Date stampUpdateDate;

}
